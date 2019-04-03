import requests
from lxml import html

url = "https://movie.douban.com/top250"

response = requests.get(url)

print(response.status_code)
print(response.encoding)
print(response.headers)
# print(response.text)
# print(response.conte nt)

html.fromstring()

