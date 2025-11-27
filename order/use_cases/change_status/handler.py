from .dto import ChangeStatusDTO, ChangeStatusOutputDTO
from flask import jsonify

def change_order_status_handler(connection, data: ChangeStatusDTO):
    cur = connection.cursor()

    cur.execute("UPDATE order_table SET order_status = %s WHERE id = %s", (data.status, data.orderId))
    connection.commit()
    cur.close()

    response_dto: ChangeStatusOutputDTO = ChangeStatusOutputDTO(orderId=data.orderId, status=data.status)

    return jsonify(response_dto), 200