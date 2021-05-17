<template>
  <div>
    <div class="container" style="background-color: #2c343c">
      <div class="schart-box">
        <div style="width: 600px; height: 300px" id="main"></div>
      </div>
      <div class="schart-box">
        <div style="width: 600px; height: 300px" id="main1"></div>
      </div>
      <div class="schart-box">
        <div style="width: 600px; height: 300px" id="main2"></div>
      </div>
      <div class="schart-box">
        <div style="width: 600px; height: 300px" id="main3"></div>
      </div>
    </div>
  </div>
</template>

<script>

import {getDoctorAgeData, getPatientAgeData, getPatientDiagnoseData} from "../api/admin";
import $ from 'jquery'

let echarts = require('echarts/lib/echarts');
require('echarts/lib/chart/bar');
require('echarts/lib/component/title');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/visualMap');
require('echarts/lib/chart/pie');
require('echarts/lib/component/grid');
require('echarts/lib/component/geo');
require('echarts/lib/component/dataZoom');


export default {
  name: 'Echarts',
  mounted() {
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    getDoctorAgeData().then(res => {
      console.log(res.data.data);
      if (res.data.code == 0) {
        //list 为从后端导入的数据
        //todo:data的类型问题
        // option.series.data = res.data.data;
        console.log("final type message: ", res.data.data);
        //console.log("option.series.data",option.series.data)
        //option &&
        myChart.setOption({
          backgroundColor: '#2c343c',

          title: {
            text: '医生年龄分布图',
            left: 'center',
            top: 0,
            textStyle: {
              color: '#ccc'
            }
          },
          roseType: 'angle',
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          //单一颜色的变化图
          visualMap: {
            show: false,
            min: 0,
            max: 10,
            inRange: {
              colorLightness: [0.1, 1]
            }
          },
          series: [{
            name: '医生年龄',
            type: 'pie',
            radius: [20, 110],
            center: ['50%', '55%'],
            //todo 将后台数据赋值给data
            data: res.data.data.sort(function (a, b) {
              return a.value - b.value;
            }),
            roseType: 'radius',
            label: {
              color: 'rgba(250,246,246,0.76)'
            },
            labelLine: {
              lineStyle: {
                color: 'rgba(255,255,255,0.71)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            },
            itemStyle: {
              borderRadius: 5,
              color: '#580eaa',
              shadowBlur: 0,
              shadowColor: 'rgba(23,22,22,0.5)'
            },

            animationType: 'scale',
            animationEasing: 'elasticOut',
            // eslint-disable-next-line no-unused-vars
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }]
        });
      } else {
        this.$message.error("读取类型信息失败");
      }
    });


    var chartDom1 = document.getElementById('main1');
    var myChart1 = echarts.init(chartDom1);
    getPatientAgeData().then(res => {
      console.log(res.data.data);
      if (res.data.code == 0) {
        //list 为从后端导入的数据
        //todo:data的类型问题
        // option.series.data = res.data.data;
        console.log("final type message: ", res.data.data);
        //console.log("option.series.data",option.series.data)
        //option &&
        myChart1.setOption({
          backgroundColor: '#2c343c',

          title: {
            text: '患者年龄分布图',
            left: 'center',
            top: 0,
            textStyle: {
              color: '#ccc'
            }
          },
          roseType: 'angle',
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          // //单一颜色的变化图
          // visualMap: {
          //   show: false,
          //   min: 0,
          //   max: 10,
          //   inRange: {
          //     colorLightness: [0.5, 1]
          //   }
          // },
          series: [{
            name: '患者年龄',
            type: 'pie',
            radius: '80%',
            center: ['50%', '55%'],
            //todo 将后台数据赋值给data
            data: res.data.data.sort(function (a, b) {
              return a.value - b.value;
            }),
            roseType: 'radius',
            label: {
              color: 'rgba(250,246,246,0.76)'
            },
            labelLine: {
              lineStyle: {
                color: 'rgba(255,255,255,0.71)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            },
            itemStyle: {
              borderRadius: 5
              // color: 'rgba(241,38,62,0.68)',
              //     shadowBlur: 200,
              //     shadowColor: 'rgba(23,22,22,0.5)'
            },

            animationType: 'scale',
            animationEasing: 'elasticOut',
            // eslint-disable-next-line no-unused-vars
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }]
        });
      } else {
        this.$message.error("读取类型信息失败");
      }
    });


    var ROOT_PATH = 'https://cdn.jsdelivr.net/gh/apache/echarts-website@asf-site/examples';
    var chartDom2 = document.getElementById('main2');
    var myChart2 = echarts.init(chartDom2);
    $.get(ROOT_PATH + '/data/asset/geo/Veins_Medical_Diagram_clip_art.svg', function (svg) {
      echarts.registerMap('organ_diagram', {svg: svg});


      getPatientDiagnoseData().then(res => {
        console.log(res.data.data);
        if (res.data.code == 0) {
          //list 为从后端导入的数据
          //todo:data的类型问题
          // option.series.data = res.data.data;
          console.log("final type message: ", res.data.data);
          //console.log("option.series.data",option.series.data)
          //option &&
          myChart2.setOption({
            tooltip: {},
            geo: {
              left: 10,
              right: '50%',
              map: 'organ_diagram',
              selectedMode: 'multiple',
              emphasis: {
                focus: 'self',
                itemStyle: {
                  color: null
                },
                label: {
                  position: 'bottom',
                  distance: 0,
                  textBorderColor: '#fff',
                  textBorderWidth: 2
                }
              },
              blur: {},
              select: {
                itemStyle: {
                  color: '#b50205'
                },
                label: {
                  show: false,
                  textBorderColor: '#fff',
                  textBorderWidth: 2
                }
              }
            },
            textStyle:{
              fontSize:30,
              fontWeight:"bolder"
            },
            grid: {
              left: '60%',
              top: '20%',
              bottom: '20%'
            },
            xAxis: {},
            yAxis: {
              // 传入
              data: res.data.data.names
                  // ['heart', 'large-intestine', 'small-intestine', 'spleen', 'kidney', 'lung', 'liver','hair']
            },
            series: [{
              type: 'bar',
              showBackground: true,
              itemStyle: {
                color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                      {offset: 0, color: '#83bff6'},
                      {offset: 0.5, color: '#188df0'},
                      {offset: 1, color: '#188df0'}
                    ]
                )
              },
              emphasis: {
                itemStyle: {
                  color: new echarts.graphic.LinearGradient(
                      0, 0, 0, 1,
                      [
                        {offset: 0, color: '#2378f7'},
                        {offset: 0.7, color: '#2378f7'},
                        {offset: 1, color: '#83bff6'}
                      ]
                  )
                }
              },
              //传入
              data: res.data.data.values
                  // [1, 3, 1, 5, 9, 2, 3,2]
            }]
          });
      // var zoomSize = 6;
      // myChart.on('click', function (params) {
      //   console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
      //   myChart.dispatchAction({
      //     type: 'dataZoom',
      //     startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
      //     endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
      //   });
      // });
          myChart2.on('mouseover', {seriesIndex: 0}, function (event) {
            myChart2.dispatchAction({
              type: 'highlight',
              geoIndex: 0,
              name: event.name
            });
          });
          myChart2.on('mouseout', {seriesIndex: 0}, function (event) {
            myChart2.dispatchAction({
              type: 'downplay',
              geoIndex: 0,
              name: event.name
            });
          });
        } else {
          this.$message.error("读取类型信息失败");
        }
      });
    });

    var chartDom3 = document.getElementById('main3');
    var myChart3 = echarts.init(chartDom3);
    getPatientDiagnoseData().then(res => {
      console.log(res.data.data);
      if (res.data.code == 0) {
        //list 为从后端导入的数据
        //todo:data的类型问题
        // option.series.data = res.data.data;
        console.log("final type message: ", res.data.data);
        //console.log("option.series.data",option.series.data)
        //option &&
        myChart3.setOption({
          backgroundColor: '#2c343c',

          title: {
            text: '患者病因分布图',
            left: 'center',
            top: 0,
            textStyle: {
              color: '#ccc'
            }
          },
          roseType: 'angle',
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          //单一颜色的变化图
          // visualMap: {
          //   show: false,
          //   min: 0,
          //   max: 10,
          //   inRange: {
          //     colorLightness: [0, 1]
          //   }
          // },
          series: [{
            name: '患者病因',
            type: 'pie',
            radius: [20,120],
            center: ['50%', '55%'],
            //todo 将后台数据赋值给data
            data: res.data.data.list.sort(function (a, b) {
              return a.value - b.value;
            }),
            roseType: 'radius',
            label: {
              color: 'rgba(250,246,246,0.76)'
            },
            labelLine: {
              lineStyle: {
                color: 'rgba(255,255,255,0.71)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            },
            itemStyle: {
              normal: {
                shadowBlur: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
              // color: '#0731af',
              //     shadowBlur: 200,
              //     shadowColor: 'rgba(23,22,22,0.5)'
            },

            animationType: 'scale',
            animationEasing: 'elasticOut',
            // eslint-disable-next-line no-unused-vars
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }]
        });
      } else {
        this.$message.error("读取类型信息失败");
      }
    });


  },
};
</script>

<style scoped>
.schart-box {
  display: inline-block;
  margin: 20px;
}

.schart {
  width: 600px;
  height: 400px;
}

.content-title {
  clear: both;
  font-weight: 400;
  line-height: 50px;
  margin: 10px 0;
  font-size: 22px;
  color: #1f2f3d;
}
</style>