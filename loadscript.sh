path="http://debuggerdemo.appspot.com"
path="http://localhost:8080"

curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1

while :
do
sleep 1
sleep 1
sleep 1

 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign
sleep 1
 curl $path?deleteall
sleep 1
 curl -X POST --data "content=kiss&guestbookName=default" $path/sign
sleep 1
 curl -X POST --data "content=kiss dog&guestbookName=default" $path/sign

done



