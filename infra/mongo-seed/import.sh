echo "Importing data"
mongoimport --host api-db -u root -p reading --authenticationDatabase admin -d reading_db -c book --type CSV --file /mongo-seed/reading_db_book.csv --headerline;
mongoimport --host api-db -u root -p reading --authenticationDatabase admin -d reading_db -c customer --type CSV --file /mongo-seed/reading_db_customer.csv --headerline;
echo "Done importing."
exit 0