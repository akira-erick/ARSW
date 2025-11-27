from .dto import MakeOrderCompensationDTO, MakeOrderCompensationResponseDTO
from flask import jsonify

def make_order_compensation_handler(connection, data: MakeOrderCompensationDTO):

    match data.reason:
        case "OUT_OF_STOCK":
            status = "CANCELLED"
        case "PAYMENT_ERROR":
            status = "REFUNDED"
        case _:
            return jsonify(MakeOrderCompensationResponseDTO(result=str("FAILED"))), 400
        
    cur = connection.cursor()
    cur.execute("UPDATE order_table SET order_status = %s WHERE id = %s", (status, data.orderId))
    connection.commit()
    cur.close()

    response_dto: MakeOrderCompensationResponseDTO = MakeOrderCompensationResponseDTO(result=str("SUCCESS"))

    return jsonify(response_dto), 200
