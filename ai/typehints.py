from typing import TypedDict


class TrainInfo(TypedDict):
    accuracy: float


class PredictTender(TypedDict):
    tenderId: int
    text: str


class Prediction(TypedDict):
    tenderId: int
    label: int
    confidence: int


class TrainTender(TypedDict):
    tenderId: int
    text: str
    label: str
