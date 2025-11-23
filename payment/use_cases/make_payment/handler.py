from .dto import MakePaymentResponseDTO, MakePaymentDTO
from flask import jsonify

def make_payment_handler(connection, data: MakePaymentDTO):

    if data.work is False:
        return jsonify({"error": "Simulated payment failure"}), 402

    status = "Processed"
    
    cur = connection.cursor()

    cur.execute("INSERT INTO payment_table (order_id, amount, payment_status) VALUES (%s, %s, %s) RETURNING id", (data.orderId, data.amount, status))
    payment_id = cur.fetchone()["id"]
    connection.commit()
    cur.close()

    response_dto: MakePaymentResponseDTO = MakePaymentResponseDTO(paymentId=str(payment_id), status=str(status))

    return jsonify(response_dto), 201