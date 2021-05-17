<template>
  <div class="container" style="background-color: #2c343c">
    <div class="mgb22">
      <div class="user-info" style="height: 60px">
        <img src="../assets/img/img.jpg" class="user-avator" alt/>
        <div class="user-info-cont" style="text-align: center;margin-right: 450px">
          <div class="user-info-name">欢迎{{ role }}{{ name }}进入系统</div>
        </div>
      </div>
    </div>
    <div style="width: 1000px; height: 600px" id="main"></div>
  </div>
</template>

<script>
const echarts = require('echarts/lib/echarts');
require('echarts/lib/chart/sunburst');

export default {
  mounted() {
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;
    var colors = ['#FFAE57', '#FF7853', '#EA5151', '#CC3F57', '#9A2555'];
    var bgColor = '#2c343c';
    var itemStyle = {
      star5: {
        color: colors[0]
      },
      star4: {
        color: colors[1]
      },
      star3: {
        color: colors[2]
      },
      star2: {
        color: colors[3]
      }
    };
    var data = [{
          name: '医生',
          itemStyle: {
            color: colors[1]
          },
          children: [{
            name: '用户信息',
            children: [{
              name: '☆',
              children: [{
                name: '查看患者信息'
              }, {
                name: '添加诊断信息'
              }]
            }]
          }, {
            name: '医生信息',
            children: [{
              name: '🌙',
              children: [{
                name: '修改医生信息'
              }]
            }]
          }, {
            name: '影像管理',
            children: [{
              name: '✈',
              children: [{
                name: '下载医学影像'
              }, {
                name: '上传医学影像'
              }, {
                name: '删除医学影像'
              }]
            }]
          }, {
            name: '医患信息',
            children: [{
              name: '❄',
              children: [{
                name: '添加医患对应信息'
              }]
            }]
          }]
        }, {
          name: '用户',
          itemStyle: {
            color: colors[2]
          },
          children: [{
            name: '信息',
            children: [{
              name: '☆',
              children: [{
                name: '查看个人信息'
              }, {
                name: '修改个人信息'
              }]
            }]
          }, {
            name: '影像',
            children: [{
              name: '🌙',
              children: [{
                name: '下载医学影像'
              }]
            }]
          }]
        }, {
          name: '管理员',
          itemStyle: {
            color: colors[3]
          }, children: [{
            name: '数据分析',
            children: [{
              name: '✈',
              children: [{
                name: '医生分析'
              }, {
                name: '患者分析'
              }]
            }]
          }, {
            name: '信息管理',
            children: [{
              name: '❄',
              children: [{
                name: '医学影像信息管理'
              }, {
                name: '医患信息管理'
              }, {
                name: '用户信息管理'
              }]
            }]
          }, {
            name: '医生信息',
            children: [{
              name: '☆',
              children: [{
                name: '添加医生信息'
              }, {
                name: '修改医生信息'
              }]
            }]
          }]
        }];

    for (var j = 0; j < data.length; ++
        j
    ) {
      var level1 = data[j].children;
      for (var i = 0; i < level1.length; ++i) {
        var block = level1[i].children;
        var bookScore = [];
        var bookScoreId;
        for (var star = 0; star < block.length; ++star) {
          var style = (function (name) {
            switch (name) {
              case '☆':
                bookScoreId = 0;
                return itemStyle.star5;
              case '🌙':
                bookScoreId = 1;
                return itemStyle.star4;
              case '✈':
                bookScoreId = 2;
                return itemStyle.star3;
              case '❄':
                bookScoreId = 3;
                return itemStyle.star2;
            }
          })(block[star].name);

          block[star].label = {
            color: style.color,
            downplay: {
              opacity: 0.5
            }
          };

          if (block[star].children) {
            style = {
              opacity: 1,
              color: style.color
            };
            block[star].children.forEach(function (book) {
              book.value = 1;
              book.itemStyle = style;

              book.label = {
                color: style.color
              };

              var value = 1;
              if (bookScoreId === 0 || bookScoreId === 3) {
                value = 5;
              }

              if (bookScore[bookScoreId]) {
                bookScore[bookScoreId].value += value;
              } else {
                bookScore[bookScoreId] = {
                  color: colors[bookScoreId],
                  value: value
                };
              }
            });
          }
        }

        level1[i].itemStyle = {
          color: data[j].itemStyle.color
        };
      }
    }

    option = {
      backgroundColor: bgColor,
      color: colors,
      series: [{
        type: 'sunburst',
        center: ['62%', '45%'],
        data: data,
        sort: function (a, b) {
          if (a.depth === 1) {
            return b.getValue() - a.getValue();
          } else {
            return a.dataIndex - b.dataIndex;
          }
        },
        label: {
          rotate: 'radial',
          color: bgColor,
          fontSize: 14
        },
        itemStyle: {
          borderColor: bgColor,
          borderWidth: 2
        },
        levels: [{}, {
          r0: 0,
          r: 40,
          label: {
            rotate: 0
          }
        }, {
          r0: 40,
          r: 105
        }, {
          r0: 115,
          r: 140,
          itemStyle: {
            shadowBlur: 2,
            shadowColor: colors[2],
            color: 'transparent'
          },
          label: {
            rotate: 'tangential',
            fontSize: 15,
            color: colors[0]
          }
        }, {
          r0: 140,
          r: 145,
          itemStyle: {
            shadowBlur: 80,
            shadowColor: colors[0]
          },
          label: {
            position: 'outside',
            textShadowBlur: 5,
            textShadowColor: '#333'
          },
          downplay: {
            label: {
              opacity: 0.5
            }
          }
        }]
      }]
    };

    option && myChart.setOption(option);
  },
  name: "dashboard",
  data() {
    return {
      name: localStorage.getItem("ms_username"),
    };
  }
  ,
  computed: {
    role() {
      //console.log(localStorage.getItem("ms_type"));
      const role = localStorage.getItem("ms_type");
      //console.log(role);
      if (role == 1) {
        // console.log("bingo!");
        return "管理员";
      } else if (role == 2) {
        return "医生";
      } else if (role == 3) {
        return "用户";
      } else {
        return "用户";
      }
    }
  }
  ,
  methods: {}
}
;
</script>

<style scoped>

.user-info {
  display: flex;
  align-items: center;
}

.user-avator {
  margin-left: 350px;
  width: 80px;
  height: 80px;
  border-radius: 50%;
}

.user-info-cont {
  flex: 1;
  font-size: 14px;
}

.user-info-name {
  font-size: 30px;
  color: #dddddd;
  font-family: PingFang SC, Helvetica Neue, Helvetica, microsoft yahei, arial, STHeiTi, sans-serif;
}

.mgb22 {
  height: 80px;
  width: 100%;
}

</style>

/*
一层层上色
最外层上底色
内侧层如果设置了会覆盖之前的层
*/