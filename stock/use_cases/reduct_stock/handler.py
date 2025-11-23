from flask import jsonify
from psycopg2 import IntegrityError
from .dto import ReductStockDTO, ReductStockResponseDTO

def reduct_stock_handler(connection, data: ReductStockDTO):
    cur = connection.cursor()

    try:
        cur.execute(
            "UPDATE stock_table SET quantity = quantity - %s WHERE id = %s RETURNING id, quantity", 
            (data.quantity, data.id)
        )
        if cur.rowcount == 0:
            return jsonify({"error": "Item not found"}), 404
        
        result = cur.fetchone()
        connection.commit()
        response_dto: ReductStockResponseDTO = ReductStockResponseDTO(id=result["id"], quantity=result["quantity"])

        return jsonify(response_dto), 200
    except IntegrityError:
        connection.rollback()
        return jsonify({"error": "Insufficient stock"}), 400
    finally:
        cur.close()