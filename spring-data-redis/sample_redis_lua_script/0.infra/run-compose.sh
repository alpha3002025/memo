cnt_sample_network=`docker network ls --filter name=sample-network | wc -l`
cnt_sample_network=$(($cnt_sample_network -1))

if [ $cnt_sample_network -eq 0 ]
then
  echo "'sample-network' 가 존재하지 않습니다. 'sample-network'를 새로 생성합니다."
  docker network create sample-network
fi

docker-compose -f common.yml -f docker-compose.yml up -d