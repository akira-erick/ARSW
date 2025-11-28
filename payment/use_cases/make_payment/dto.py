from dataclasses import dataclass

@dataclass
class MakePaymentDTO:
    orderId: int
    amount: float

@dataclass
class MakePaymentResponseDTO:
    paymentId: int
    status: str