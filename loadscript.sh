path="http://debuggerdemo.appspot.com"
path="http://localhost:8080"

echo $path/sign
curl -X POST --data "content=kiss&guestbookName=default" $path/sign


while :
do
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
 curl $path?deleteall
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign

done



