import sqlite3
from pathlib import Path

import pandas as pd

BASE_DIR = Path(__file__).parent
DB_PATH = BASE_DIR / "shipment_database.db"
DATA_DIR = BASE_DIR / "data"


def load_shipping_data_0() -> pd.DataFrame:
    df = pd.read_csv(DATA_DIR / "shipping_data_0.csv")
    return pd.DataFrame(
        {
            "product": df["product"],
            "quantity": df["product_quantity"],
            "origin": df["origin_warehouse"],
            "destination": df["destination_store"],
        }
    )


def load_shipping_data_1_and_2() -> pd.DataFrame:
    products = pd.read_csv(DATA_DIR / "shipping_data_1.csv")
    routes = pd.read_csv(DATA_DIR / "shipping_data_2.csv")

    quantities = (
        products.groupby(["shipment_identifier", "product"])
        .size()
        .reset_index(name="quantity")
    )
    merged = quantities.merge(routes, on="shipment_identifier")

    return pd.DataFrame(
        {
            "product": merged["product"],
            "quantity": merged["quantity"],
            "origin": merged["origin_warehouse"],
            "destination": merged["destination_store"],
        }
    )


def insert_shipments(records: pd.DataFrame) -> None:
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()

    product_ids: dict[str, int] = {}
    for product_name in records["product"].unique():
        cursor.execute("INSERT OR IGNORE INTO product (name) VALUES (?)", (product_name,))
        cursor.execute("SELECT id FROM product WHERE name = ?", (product_name,))
        product_ids[product_name] = cursor.fetchone()[0]

    for _, row in records.iterrows():
        cursor.execute(
            "INSERT INTO shipment (product_id, quantity, origin, destination) VALUES (?, ?, ?, ?)",
            (
                product_ids[row["product"]],
                int(row["quantity"]),
                row["origin"],
                row["destination"],
            ),
        )

    conn.commit()
    conn.close()


def main() -> None:
    records = pd.concat(
        [load_shipping_data_0(), load_shipping_data_1_and_2()],
        ignore_index=True,
    )
    insert_shipments(records)
    print(f"Inserted {len(records)} shipment records.")


if __name__ == "__main__":
    main()
