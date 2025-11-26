from dataclasses import dataclass

@dataclass
class ChangeStatusDTO:
    orderId: int
    status: str