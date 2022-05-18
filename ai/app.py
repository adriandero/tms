from typing import List

import flask
from flask import Flask, request, Response

from model_manager import load_SvmManager
from typehints import *

svm_manager = load_SvmManager()

app = Flask(__name__)


@app.route("/")
def hello() -> str:
    return "<h1 style='color:blue'> This is the Classification REST-Service.</h1>"


@app.route('/predict', methods=['POST'])
def predict() -> Response:
    predict_tender: PredictTender = request.json
    response: Prediction = svm_manager.predict(predict_tender)
    return flask.jsonify(response)


@app.route('/train', methods=['POST'])
def train() -> Response:
    train_tenders: List[TrainTender] = request.json
    response: TrainInfo = svm_manager.train(train_tenders)
    return flask.jsonify(response)


@app.route('/info', methods=['GET'])
def info() -> Response:
    response = svm_manager.info()
    return flask.jsonify(response)

if __name__ == '__main__':
    from waitress import serve
    serve(app, port=5000)

