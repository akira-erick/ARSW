from dataclasses import dataclass

@dataclass
class MakeOrderCompensationDTO:
    orderId: int
    reason: str

@dataclass
class MakeOrderCompensationResponseDTO:
    result: str