from dataclasses import dataclass

@dataclass
class ChangeStatusDTO:
    orderId: int
    status: str

@dataclass
class ChangeStatusOutputDTO:
    orderId: int
    status: str