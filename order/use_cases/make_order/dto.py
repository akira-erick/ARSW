from dataclasses import dataclass

@dataclass
class MakeOrderDTO:
    customerName: str
    item: str
    quantity: str

@dataclass
class MakeOrderResponseDTO:
    orderId: int
    status: str