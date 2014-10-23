 # usage: nohup ./loadscript.sh debuggerdemo.appspot.com
 # usage: nohup ./loadscript.sh localhost:8080

  path="http://$1"


echo $path



while :
do
   curl -X POST --data "content=kiss&guestbookName=default" $path/sign
   sleep 1
   curl -X POST --data "content=my dog drinks&guestbookName=default" $path/sign
   sleep 1
   curl -X POST --data "content=hey kissed in the city&guestbookName=default" $path/sign
   sleep 1
   curl -X POST --data "content=I am happy its hot&guestbookName=default" $path/sign
   sleep 1
   curl -X POST --data "content=hot in the city today&guestbookName=default" $path/sign
   sleep 1
   curl -X POST --data "content=is it hot in the city?&guestbookName=default" $path/sign
   sleep 1
   curl -X POST --data "content=Do you want to get drinks in the city tongiht?&guestbookName=default" $path/sign
   sleep 1
   curl $path/deleteall
done



