from dataclasses import dataclass

@dataclass
class MakePaymentDTO:
    orderId: int
    amount: float
    work: bool

@dataclass
class MakePaymentResponseDTO:
    id: int
    status: str