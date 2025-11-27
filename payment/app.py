import os
from flask import Flask, request, jsonify

from .db_service import get_db_connection

from .use_cases.make_payment.dto import MakePaymentDTO
from .use_cases.make_payment.handler import make_payment_handler

from .use_cases.make_payment_compensation.dto import MakePaymentCompensationDTO
from .use_cases.make_payment_compensation.handler import make_payment_compensation_handler

app = Flask(__name__)

@app.route('/payment', methods=['POST'])
def make_payment():
    conn = get_db_connection()

    data = request.get_json()
    if not data:
        return {"error": "Missing data"}, 400
    try:
        order_id = data['orderId']
        amount = data['amount']
        work = data["work"]

        dto: MakePaymentDTO = MakePaymentDTO(
            orderId=order_id,
            amount=amount,
            work=work
        )
        response = make_payment_handler(connection=conn, data=dto)

        return response
    except KeyError as e:
        return jsonify({"error": f"Missing field: {str(e)}"}), 400
    finally:
        conn.close()

@app.route('/payment/compensate', methods=['POST'])
def make_payment_compensation():
    conn = get_db_connection()

    data = request.get_json()
    if not data:
        return {"result": "FAILED"}, 400
    try:
        payment_id = data['paymentId']
        reason = data['reason']

        dto: MakePaymentCompensationDTO = MakePaymentCompensationDTO(
            paymentId=payment_id,
            reason=reason
        )
        response = make_payment_compensation_handler(connection=conn, data=dto)

        return response
    except KeyError as e:
        return jsonify({"result": "FAILED"}), 400
    finally:
        conn.close()

@app.route('/health', methods=['GET'])
def health_check():
    return "Order service is healthy!"

if __name__ == '__main__':
    port = int(os.getenv('HOST_PORT_PAYMENT', 8082))
    app.run(debug=True, host='0.0.0.0', port = port, use_reloader=False)