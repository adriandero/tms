import os
import pickle
from typing import List

import numpy as np
import pandas as pd
from sklearn import svm
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline

from preprocessing import clean, clean_df, stop_words
from typehints import *

dirname = os.path.dirname(__file__)
SAVE_FILE = os.path.join(dirname, 'saves/model_manager.pkl')


class SvmManager:
    def __init__(self):
        self.vectorizer = TfidfVectorizer(stop_words=stop_words)

        self.linear_svc = svm.SVC(kernel='linear', probability=True)
        self.rbf_svc = svm.SVC(probability=True)

        self.svcs = (self.linear_svc, self.rbf_svc)

        self.pipes: List[Pipeline] = self.create_pipes()

        self.accuracies = []
        self.best_model_idx = None

    def create_pipes(self) -> List[Pipeline]:
        pipes = []
        # vec = TfidfVectorizer(stop_words=stop_words)
        for svc in self.svcs:
            pipes.append(Pipeline([('vectorizer', self.vectorizer),
                                   ('classifier', svc)]))
        return pipes

    def train(self, tenders: List[TrainTender]) -> TrainInfo:
        tenders_df = clean_df(pd.DataFrame(tenders))

        X_train, X_test, y_train, y_test = split_data(tenders_df)

        accuracies = []
        for pipe in self.pipes:
            pipe.fit(X_train, y_train)
            accuracies.append(pipe.score(X_test, y_test))
        self.accuracies = accuracies
        self.best_model_idx = self.accuracies.index(max(self.accuracies))

        save(self)
        return {'accuracy': self.accuracies[self.best_model_idx]}

    def predict(self, tender: PredictTender) -> Prediction:
        x = [clean(tender['text'])]
        # print(self.pipes)
        # print(self.accuracies)
        # print(self.best_model_idx)
        best_model = self.pipes[self.best_model_idx]
        prediction = best_model.predict(x)

        probas = best_model.predict_proba(x)[0]  # [0] -> get the first (only) sample
        confidence = probas[np.argmax(probas)]

        return {
            'tenderId': tender['tenderId'],
            'label': prediction[0],
            'confidence': int(confidence * 100)
        }

    def info(self) -> dict:
        return {'models': [{'name': str(model), 'acc': acc} for model, acc in zip(self.svcs, self.accuracies)]}


def split_data(tenders: pd.DataFrame):
    y = tenders.label.values
    X_train, X_test, y_train, y_test = train_test_split(
        tenders.clean.values, y,
        test_size=0.3,
        shuffle=True,
        stratify=y  # (for y imbalances) https://scikit-learn.org/stable/modules/cross_validation.html#stratification
    )
    return X_test, X_train, y_test, y_train


def save(svm_mngr: SvmManager):
    with open(SAVE_FILE, 'wb') as f:
        pickle.dump(svm_mngr, f)


def load_SvmManager() -> SvmManager:
    with open(SAVE_FILE, 'rb') as f:
        try:
            return pickle.load(f)
        except EOFError:
            return SvmManager()