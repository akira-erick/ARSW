import os
from flask import Flask, request, jsonify

from .db_service import get_db_connection

from .use_cases.reduct_stock.dto import ReductStockDTO
from .use_cases.reduct_stock.handler import reduct_stock_handler

app = Flask(__name__)

@app.route('/stock/reduct', methods=['POST'])
def reduct_stock():
    conn = get_db_connection()

    data = request.get_json()
    if not data:
        return {"error": "Missing data"}, 400
    try:
        item_id = data['id']
        quantity = data['quantity']

        dto: ReductStockDTO = ReductStockDTO(
            id=item_id,
            quantity=quantity
        )
        response = reduct_stock_handler(connection=conn, data=dto)

        return response
    except KeyError as e:
        return jsonify({"error": f"Missing field: {str(e)}"}), 400
    finally:
        conn.close()

@app.route('/stock/compensate', methods=['POST'])
def reduct_stock_compensation():
    return "Stock reduction compensation executed!"

if __name__ == '__main__':
    port = int(os.getenv('HOST_PORT_STOCK', 8083))
    app.run(debug=True, host='0.0.0.0', port = port, use_reloader=False)