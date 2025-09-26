#!/usr/bin/env bash
set -euo pipefail

DB_NAME="${DB_NAME:-taskdb}"
DB_USER="${DB_USER:-postgres}"
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_PASS="${DB_PASS:-admin}"
OUT_DIR="docker/init"

mkdir -p "$OUT_DIR"

echo "Dumping schema -> $OUT_DIR/schema.sql"
PGPASSWORD="$DB_PASS" pg_dump -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -s --no-owner --no-acl -f "$OUT_DIR/schema.sql"

echo "Dumping data -> $OUT_DIR/data.sql"
PGPASSWORD="$DB_PASS" pg_dump -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -a --inserts --no-owner --no-acl -f "$OUT_DIR/data.sql"

echo "Combining schema and data into a single sql file"
cat "$OUT_DIR/schema.sql" "$OUT_DIR/data.sql" > "$OUT_DIR/init.sql"
echo "Database dump completed successfully."

