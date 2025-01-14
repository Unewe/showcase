: ${HOST=localhost}
: ${PORT=7700}
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

set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

assertCurl 200  "curl http://$HOST:$PORT/product-composite/$PRODUCT_ID -s"
assertEqual "1" "${RESPONSE:(6):(1)}"
