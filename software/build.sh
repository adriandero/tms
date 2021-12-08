docker build -t snt/tms .

if [ "$1" == "run" ]; then
  docker run snt/tms
else
  echo "If you want to build & run, do \"$(basename $0) run\""
fi