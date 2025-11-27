import os
from flask import Flask, request, jsonify

from .db_service import get_db_connection

from .use_cases.make_order.dto import MakeOrderDTO
from .use_cases.make_order.handler import make_order_handler

from.use_cases.change_status.dto import ChangeStatusDTO
from.use_cases.change_status.handler import change_order_status_handler

from .use_cases.make_order_compensation.dto import MakeOrderCompensationDTO
from .use_cases.make_order_compensation.handler import make_order_compensation_handler

app = Flask(__name__)

@app.route('/order', methods=['POST'])
def make_order():
    conn = get_db_connection()

    data = request.get_json()
    if not data:
        return {"error": "Missing data"}, 400
    try:
        customer_name = data['customerName']
        item = data['item']
        quantity = data['quantity']

        dto: MakeOrderDTO = MakeOrderDTO(
            customerName=customer_name,
            item=item,
            quantity=quantity
        )
        response = make_order_handler(connection=conn, data=dto)

        return response
    except KeyError as e:
        return jsonify({"error": f"Missing field: {str(e)}"}), 400
    finally:
        conn.close()

@app.route("/order/status", methods=['PUT'])
def change_order_status():
    conn = get_db_connection()

    data = request.get_json()
    if not data:
        return {"error": "Missing data"}, 400
    try:
        order_id = data['orderId']
        status = data['status']

        dto: ChangeStatusDTO = ChangeStatusDTO(
            orderId=order_id,
            status=status
        )
        response = change_order_status_handler(connection=conn, data=dto)

        return response
    except KeyError as e:
        return jsonify({"error": f"Missing field: {str(e)}"}), 400
    finally:
        conn.close()


@app.route('/order/compensate', methods=['POST'])
def make_order_compensation():
    conn = get_db_connection()

    data = request.get_json()
    if not data:
        return {"result": "FAILED"}, 400
    try:
        order_id = data['orderId']
        reason = data['reason']

        dto: MakeOrderCompensationDTO = MakeOrderCompensationDTO(
            orderId=order_id,
            reason=reason
        )
        response = make_order_compensation_handler(connection=conn, data=dto)

        return response
    except KeyError as e:
        return jsonify({"result": "FAILED"}), 400
    finally:
        conn.close()

@app.route('/health', methods=['GET'])
def health_check():
    return "Order service is healthy!"

if __name__ == '__main__':
    port = int(os.getenv('HOST_PORT_ORDER', 8081))
    app.run(debug=True, host='0.0.0.0', port = port, use_reloader=False)