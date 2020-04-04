addr="input/"
addr="$addr$1"
echo "./a.out < $addr > $2"
./a.out < $addr > "$2"