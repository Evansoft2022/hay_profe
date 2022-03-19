import json, requests,sqlite3	

with open("colombia.json") as file:
	file = file.read()
	data = json.loads(file)
city = data[1]['ciudades']
con = sqlite3.connect('db.sqlite3')
cur = con.cursor()

for i in city:
	query = "insert into data_city(name)values(?)"
	args = (i,)
	cur.execute(query,args)
con.commit()
cur.close




# .encode("ascii", errors="replace")



# with open("replayScript.json", "r") as jsonFile:
#     data = json.load(jsonFile)
#     print(type(data))














