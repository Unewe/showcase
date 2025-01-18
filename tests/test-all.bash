#RUN TESTS:
#./tests/test-all.bash start stop

: ${HOST=localhost}
: ${PORT=8080}
: ${PRODUCT_ID=1}

function assertCurl() {
  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result}>3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
    echo "Test FAILED, expected HTTP Code: $expectedHttpCode, got: $httpCode, WILL ABORT!"
    echo "- FAILED command: $curlCmd"
    echo "- Response body: $RESPONSE"
    exit 1
  fi
}

function assertEqual() {
  if [ "$1" = "$2" ]
  then
    echo "Test OK, actual value: $1"
  else
    echo "Test FAILED, expected value: $2, actual value: $1"
  fi
}

function testUrl() {
  url=$@
  if curl $url -ks -f -o /dev/null
  then
    return 0
  else
    return 1
  fi
}

function waitForService() {
  url=$@
  echo -n "Wait for: $url"
  n=0
  until testUrl $url
  do
    n=$((n + 1))
    if [ $n = 100 ]
    then
      echo "Give up"
      exit 1
    else
      sleep 3
      echo -n ",retry #$n "
    fi
  done
  echo "DONE, continues..."
}

set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ = *"start"* ]]
then
  echo "Restarting test environment..."
  echo "$ docker compose down --remove-orphans"
  docker compose down --remove-orphans
  echo "$ docker compose up -d"
  docker compose up -d
fi

waitForService curl http://$HOST:$PORT/product-composite/$PRODUCT_ID

assertCurl 200  "curl http://$HOST:$PORT/product-composite/$PRODUCT_ID -s"
assertEqual "1" "${RESPONSE:(6):(1)}"

if [[ $@ = *"stop"* ]]
then
  echo "Stopping test environment..."
  echo "$ docker compose down"
  docker compose down
fi

echo "End, all tests: OK"
