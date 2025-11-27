from .dto import MakePaymentCompensationDTO, MakePaymentCompensationResponseDTO
from flask import jsonify

def make_payment_compensation_handler(connection, data: MakePaymentCompensationDTO):
    
    match data.reason:
        case "OUT_OF_STOCK":
            status = "REFUNDED"
        case _:
            return jsonify(MakePaymentCompensationResponseDTO(result=str("FAILED"))), 400
        
    cur = connection.cursor()

    cur.execute("UPDATE payment_table SET payment_status = %s WHERE id = %s", (status, data.paymentId))
    connection.commit()
    cur.close()

    response_dto: MakePaymentCompensationResponseDTO = MakePaymentCompensationResponseDTO(status=str("SUCCESS"))

    return jsonify(response_dto), 200