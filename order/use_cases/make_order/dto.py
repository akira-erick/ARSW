from dataclasses import dataclass

@dataclass
class MakeOrderDTO:
    customerName: str
    item: int
    quantity: int

@dataclass
class MakeOrderResponseDTO:
    orderId: int
    status: str