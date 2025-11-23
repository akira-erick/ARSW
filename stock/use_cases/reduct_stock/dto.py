from dataclasses import dataclass

@dataclass
class ReductStockDTO:
    id: int
    quantity: int

@dataclass
class ReductStockResponseDTO:
    id: int
    quantity: int