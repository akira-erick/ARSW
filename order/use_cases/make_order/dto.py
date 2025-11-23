from dataclasses import dataclass

@dataclass
class MakeOrderDTO:
    customerName: str
    item: str
    quantity: int

@dataclass
class MakeOrderResponseDTO:
    orderId: str
    status: str