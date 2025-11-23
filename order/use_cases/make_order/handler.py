from .dto import MakeOrderResponseDTO, MakeOrderDTO
import os
import uuid
from flask import jsonify

def make_order_handler(connection, data: MakeOrderDTO):
    status = "Created"
    
    cur = connection.cursor()

    cur.execute("INSERT INTO order_table (customer_name, item, quantity, order_status) VALUES (%s, %s, %s, %s) RETURNING id", (data.customerName, data.item, data.quantity, status))
    order_id = cur.fetchone()["id"]
    connection.commit()
    cur.close()

    response_dto: MakeOrderResponseDTO = MakeOrderResponseDTO(orderId=str(order_id), status=str(status))

    return jsonify(response_dto), 201