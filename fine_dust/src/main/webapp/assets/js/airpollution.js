$(function(){

    $.ajax({
        type:"get",
        url:"/api/finedust_hour/today",
        success:function(r){
            console.log(r);
            let avg = new Array();
            for(let i=0; i<r.data.length; i++){
                // let avg = r.data[i].;
            }

            let ctx = $("#air_status");
            let airpollutionChart = new Chart(ctx, {
                type:'bar',
                options:{
                    responsive:false,
                    // indexAxis:"y"
                },
                data:{
                    labels:['서울', '경기', '대구', '인천', '부산', '경기', '경북', '경남', '충북', '충남', '강원', '대전', '광주', '울산', '전북', '전남', '제주', '세종'],
                    datasets:[{
                        label:"대기오염 평균",
                        data:[11 , 12, 11, 13, 14, 15, 16, 17, 12, 13, 13, 11, 11, 11, 12, 14, 15, 12],
                        backgroundColor:['rhb(140, 0, 255)']
                    }]
                }
            })
        }
    })
})