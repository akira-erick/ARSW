from dataclasses import dataclass

@dataclass
class MakePaymentCompensationDTO:
    paymentId: int
    reason: str
    
@dataclass
class MakePaymentCompensationResponseDTO:
    result: str