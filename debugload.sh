
 # usage: nohup ./debuggerdemo.sh debuggerdemo.appspot.com
 # usage: nohup ./debugger.sh localhost:8080

path="http://$1"

echo $path

while :
do
   curl -X POST --data "content=tonight?&guestbookName=default" $path/sign
   sleep 1
done


