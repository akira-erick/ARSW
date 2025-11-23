from .dto import MakeOrderResponseDTO, MakeOrderDTO
import os
import uuid
from flask import jsonify

def make_order_handler(data: MakeOrderDTO):
    
    db_host = os.getenv('DB_HOST', 'db')
    db_port = os.getenv('DB_PORT', 5432)
    db_user = os.getenv('DB_USER', 'postgres')
    db_password = os.getenv('DB_PASSWORD', 'postgres')
    db_name = os.getenv('DB_NAME', 'postgres')

    print(db_host)

    order_id = uuid.uuid4()

    response_dto: MakeOrderResponseDTO = MakeOrderResponseDTO(order_id=order_id)

    return jsonify(response_dto), 201