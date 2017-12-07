<style scoped lang="less">
@import "../../styles/common.less";
@import "schedule.less";
</style>
<template>
    <div>
        <Tabs type="card" v-model="tabValue">
            <TabPane label="秒" name="秒">
                <RadioGroup v-model="second.select" vertical @on-change="secondChange(-1)">
                    <Radio label="0">
                        <span>每秒 允许的通配符[, - * /]</span>
                    </Radio>
                    <Radio label="1">
                        <span>周期从</span>
                        <InputNumber v-model="second.cycle_start" :max="58" :min="1" size="small" style="width:50px;" @on-focus="second.select=1" @on-change="secondChange(1)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="second.cycle_end" :max="59" :min="2" size="small" style="width:50px;" @on-focus="second.select=1" @on-change="secondChange(1)"></InputNumber>
                        <span> 秒</span>
                    </Radio>
                    <Radio label="2">
                        <span>从</span>
                        <InputNumber v-model="second.second_start" :max="59" :min="0" size="small" style="width:50px;" @on-focus="second.select=2" @on-change="secondChange(2)"></InputNumber>
                        <span> 秒开始，每 </span>
                        <InputNumber v-model="second.second_end" :max="59" :min="1" size="small" style="width:50px;" @on-focus="second.select=2" @on-change="secondChange(2)"></InputNumber>
                        <span> 秒执行一次</span>
                    </Radio>
                    <Radio label="3">
                        <span>指定</span>
                    </Radio>
                </RadioGroup>
                <CheckboxGroup v-model="second.selected" @on-change="secondChange(3)">
                    <Checkbox v-for="item of second.list" :key="item" :label="item">{{item}}</Checkbox>
                </CheckboxGroup>
            </TabPane>
            <TabPane label="分钟" name="分钟">
                <RadioGroup v-model="minutes.select" vertical @on-change="minutesChange(-1)">
                    <Radio label="0">
                        <span>分钟 允许的通配符[, - * /]</span>
                    </Radio>
                    <Radio label="1">
                        <span>周期从</span>
                        <InputNumber v-model="minutes.cycle_start" :max="58" :min="1" size="small" style="width:50px;" @on-focus="minutes.select=1" @on-change="minutesChange(1)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="minutes.cycle_end" :max="59" :min="2" size="small" style="width:50px;" @on-focus="minutes.select=1" @on-change="minutesChange(1)"></InputNumber>
                        <span> 分钟</span>
                    </Radio>
                    <Radio label="2">
                        <span>从</span>
                        <InputNumber v-model="minutes.min_start" :max="59" :min="0" size="small" style="width:50px;" @on-focus="minutes.select=2" @on-change="minutesChange(2)"></InputNumber>
                        <span> 分钟开始，每 </span>
                        <InputNumber v-model="minutes.min_end" :max="59" :min="1" size="small" style="width:50px;" @on-focus="minutes.select=2" @on-change="minutesChange(2)"></InputNumber>
                        <span> 分钟执行一次</span>
                    </Radio>
                    <Radio label="3">
                        <span>指定</span>
                    </Radio>
                </RadioGroup>
                <CheckboxGroup v-model="minutes.selected" @on-change="minutesChange(3)">
                    <Checkbox v-for="item of minutes.list" :key="item" :label="item">{{item}}</Checkbox>
                </CheckboxGroup>
            </TabPane>
            <TabPane label="小时" name="小时">
                <RadioGroup v-model="hour.select" vertical @on-change="hourChange(-1)">
                    <Radio label="0">
                        <span>小时 允许的通配符[ , - * / ]</span>
                    </Radio>
                    <Radio label="1">
                        <span>周期从</span>
                        <InputNumber v-model="hour.cycle_start" :max="23" :min="0" size="small" style="width:50px;" @on-focus="hour.select=1" @on-change="hourChange(1)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="hour.cycle_end" :max="23" :min="2" size="small" style="width:50px;" @on-focus="hour.select=1" @on-change="hourChange(1)"></InputNumber>
                        <span> 小时</span>
                    </Radio>
                    <Radio label="2">
                        <span>从</span>
                        <InputNumber v-model="hour.hour_start" :max="23" :min="0" size="small" style="width:50px;" @on-focus="hour.select=2" @on-change="hourChange(2)"></InputNumber>
                        <span> 小时开始，每 </span>
                        <InputNumber v-model="hour.hour_end" :max="23" :min="1" size="small" style="width:50px;" @on-focus="hour.select=2" @on-change="hourChange(2)"></InputNumber>
                        <span> 小时执行一次</span>
                    </Radio>
                    <Radio label="3">
                        <span>指定</span>
                    </Radio>
                </RadioGroup>
                <CheckboxGroup v-model="hour.selected" @on-change="hourChange(3)">
                    <Checkbox v-for="item of hour.list" :key="item" :label="item">{{item}}</Checkbox>
                </CheckboxGroup>
            </TabPane>
            <TabPane label="日" name="日">
                <RadioGroup v-model="day.select" vertical @on-change="dayChange(-1)">
                    <Radio label="0">
                        <span>日 允许的通配符[ , - * / L W ]</span>
                    </Radio>
                    <Radio label="1">
                        <span>不指定</span>
                    </Radio>
                    <Radio label="2">
                        <span>周期从</span>
                        <InputNumber v-model="day.cycle_start" :max="31" :min="1" size="small" style="width:50px;" @on-focus="day.select=2" @on-change="dayChange(2)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="day.cycle_end" :max="31" :min="1" size="small" style="width:50px;" @on-focus="day.select=2" @on-change="dayChange(2)"></InputNumber>
                        <span> 日</span>
                    </Radio>
                    <Radio label="3">
                        <span>从</span>
                        <InputNumber v-model="day.day_start" :max="31" :min="1" size="small" style="width:50px;" @on-focus="day.select=3" @on-change="dayChange(3)"></InputNumber>
                        <span> 日开始，每 </span>
                        <InputNumber v-model="day.day_end" :max="31" :min="1" size="small" style="width:50px;" @on-focus="day.select=3" @on-change="dayChange(3)"></InputNumber>
                        <span> 日执行一次</span>
                    </Radio>
                    <Radio label="4">
                        <span>每月</span>
                        <InputNumber v-model="day.near_work_day" :max="31" :min="1" size="small" style="width:50px;" @on-focus="day.select=4" @on-change="dayChange(4)"></InputNumber>
                        <span> 号最近的那个工作日</span>
                    </Radio>
                    <Radio label="5">
                        <span>本月最后一天</span>
                    </Radio>
                    <Radio label="6">
                        <span>指定</span>
                    </Radio>
                </RadioGroup>
                <CheckboxGroup v-model="day.selected" @on-change="dayChange(6)">
                    <Checkbox v-for="item of day.list" :key="item" :label="item">{{item}}</Checkbox>
                </CheckboxGroup>
            </TabPane>
            <TabPane label="月" name="月">
                <RadioGroup v-model="month.select" vertical @on-change="monthChange(-1)">
                    <Radio label="0">
                        <span>月 允许的通配符[ , - * / ]</span>
                    </Radio>
                    <Radio label="1">
                        <span>不指定</span>
                    </Radio>
                    <Radio label="2">
                        <span>周期从</span>
                        <InputNumber v-model="month.cycle_start" :max="12" :min="1" size="small" style="width:50px;" @on-focus="month.select=2" @on-change="monthChange(2)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="month.cycle_end" :max="12" :min="2" size="small" style="width:50px;" @on-focus="month.select=2" @on-change="monthChange(2)"></InputNumber>
                        <span> 月</span>
                    </Radio>
                    <Radio label="3">
                        <span>从</span>
                        <InputNumber v-model="month.month_start" :max="12" :min="1" size="small" style="width:50px;" @on-focus="month.select=3" @on-change="monthChange(3)"></InputNumber>
                        <span> 月开始，每 </span>
                        <InputNumber v-model="month.month_end" :max="12" :min="1" size="small" style="width:50px;" @on-focus="month.select=3" @on-change="monthChange(3)"></InputNumber>
                        <span> 月执行一次</span>
                    </Radio>
                    <Radio label="4">
                        <span>指定</span>
                    </Radio>
                </RadioGroup>
                <CheckboxGroup v-model="month.selected" @on-change="monthChange(4)">
                    <Checkbox v-for="item of month.list" :key="item" :label="item">{{item}}</Checkbox>
                </CheckboxGroup>
            </TabPane>
            <TabPane label="周" name="周">
                <RadioGroup v-model="week.select" vertical @on-change="weekChange(-1)">
                    <Radio label="0">
                        <span>周 允许的通配符[ , - * / L # ]</span>
                    </Radio>
                    <Radio label="1">
                        <span>不指定</span>
                    </Radio>
                    <Radio label="2">
                        <span>周期 从星期</span>
                        <InputNumber v-model="week.cycle_start" :max="7" :min="1" size="small" style="width:50px;" @on-focus="week.select=2" @on-change="weekChange(2)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="week.cycle_end" :max="7" :min="2" size="small" style="width:50px;" @on-focus="week.select=2" @on-change="weekChange(2)"></InputNumber>
                    </Radio>
                    <Radio label="3">
                        <span>第</span>
                        <InputNumber v-model="week.week_start" :max="7" :min="1" size="small" style="width:50px;" @on-focus="week.select=3" @on-change="weekChange(3)"></InputNumber>
                        <span> 周 的星期 </span>
                        <InputNumber v-model="week.week_end" :max="7" :min="1" size="small" style="width:50px;" @on-focus="week.select=3" @on-change="weekChange(3)"></InputNumber>
                    </Radio>
                    <Radio label="4">
                        <span>本月最后一个星期</span>
                        <InputNumber v-model="week.last" :max="7" :min="1" size="small" style="width:50px;" @on-focus="week.select=4" @on-change="weekChange(4)"></InputNumber>
                    </Radio>
                    <Radio label="5">
                        <span>指定</span>
                    </Radio>
                </RadioGroup>
                <CheckboxGroup v-model="week.selected" @on-change="weekChange(5)">
                    <Checkbox v-for="item of week.list" :key="item" :label="item">{{item}}</Checkbox>
                </CheckboxGroup>
            </TabPane>
            <TabPane label="年" name="年">
                <RadioGroup v-model="year.select" vertical @on-change="yearChange(-1)">
                    <Radio label="0">
                        <span>不指定 允许的通配符[ , - * / L # ] 非必填</span>
                    </Radio>
                    <Radio label="1">
                        <span>每年</span>
                    </Radio>
                    <Radio label="2">
                        <span>周期 从</span>
                        <InputNumber v-model="year.cycle_start" :max="year.max" :min="year.min" size="small" style="width:80px;" @on-focus="year.select=2" @on-change="yearChange(2)"></InputNumber>
                        <span> - </span>
                        <InputNumber v-model="year.cycle_end" :max="year.max" :min="year.min" size="small" style="width:80px;" @on-focus="year.select=2" @on-change="yearChange(2)"></InputNumber>
                    </Radio>
                </RadioGroup>
            </TabPane>
        </Tabs>
        <Card style="width:100%;margin-top:5px;">
            <div style="text-align:center">
                <Row style="margin-bottom:5px;">
                    <Col span="3">&nbsp;</Col>
                    <Col span="3">秒</Col>
                    <Col span="3">分钟</Col>
                    <Col span="3">小时</Col>
                    <Col span="3">日</Col>
                    <Col span="3">月</Col>
                    <Col span="3">星期</Col>
                    <Col span="3">年</Col>
                </Row>
                <Row style="margin-bottom:5px;">
                    <Col span="3">表达式字段：</Col>
                    <Col span="3" style="padding-right:2px;">
                        <Input style="width:100%" readonly v-model="second.cron"></Input>
                    </Col>
                    <Col span="3" style="padding-right:2px;">
                        <Input style="width:100%" readonly v-model="minutes.cron"></Input>
                    </Col>
                    <Col span="3" style="padding-right:2px;">
                        <Input style="width:100%" readonly v-model="hour.cron"></Input>
                    </Col>
                    <Col span="3" style="padding-right:2px;">
                        <Input style="width:100%" readonly v-model="day.cron"></Input>
                    </Col>
                    <Col span="3" style="padding-right:2px;">
                        <Input style="width:100%" readonly v-model="month.cron"></Input>
                    </Col>
                    <Col span="3" style="padding-right:2px;">
                        <Input style="width:100%" readonly v-model="week.cron"></Input>
                    </Col>
                    <Col span="3">
                        <Input style="width:100%" readonly v-model="year.cron"></Input>
                    </Col>
                </Row>
                <Row style="margin-bottom:5px;">
                    <Col span="3">Cron表达式：</Col>
                    <Col span="21">
                        <Input style="width:100%" readonly v-model="cron"></Input>
                    </Col>
                </Row>
            </div>
        </Card>
        <Row style="margin-top:15px;">
            <Col span="12" style="text-align:right;">
                <Button size="large" @click="timeModelCancel()">取消</Button>
            </Col>
            <Col span="12" style="text-align:left;padding-left:30px;">
                <Button type="primary" size="large" @click="timeModelOk()">确定</Button>
            </Col>
        </Row>
    </div>
</template>
<script>
import util from "../../libs/util";

export default {
  name: "scheduleSet",
  props:{
      currentCron:''
  },
  data() {
    return {
      cron: "",
      tabValue:'',
      second: {
        cron: "*",
        select: 0,
        cycle_start: 1,
        cycle_end: 2,
        second_start: 0,
        second_end: 1,
        list: [],
        selected: []
      },
      minutes: {
        cron: "*",
        select: 0,
        cycle_start: 1,
        cycle_end: 2,
        min_start: 0,
        min_end: 1,
        list: [],
        selected: []
      },
      hour: {
        cron: "*",
        select: 0,
        cycle_start: 0,
        cycle_end: 2,
        hour_start: 0,
        hour_end: 1,
        list: [],
        selected: []
      },
      day: {
        cron: "*",
        select: 0,
        cycle_start: 1,
        cycle_end: 2,
        day_start: 1,
        day_end: 1,
        near_work_day: 1,
        list: [],
        selected: []
      },
      month: {
        cron: "*",
        select: 0,
        cycle_start: 1,
        cycle_end: 2,
        month_start: 1,
        month_end: 1,
        list: [],
        selected: []
      },
      week: {
        cron: "?",
        select: 0,
        cycle_start: 1,
        cycle_end: 2,
        week_start: 1,
        week_end: 1,
        last: 1,
        list: [],
        selected: []
      },
      year: {
        cron: "",
        select: 0,
        cycle_start: 0,
        cycle_end: 0,
        max: 3000,
        min: 2017
      }
    };
  },
  methods: {
    cronStr() {
      return (
        this.second.cron +
        " " +
        this.minutes.cron +
        " " +
        this.hour.cron +
        " " +
        this.day.cron +
        " " +
        this.month.cron +
        " " +
        this.week.cron +
        " " +
        this.year.cron
      );
    },
    timeModelCancel() {
      this.$emit("timeCancel");
      this.init();
    },
    timeModelOk() {
      this.$emit("timeOk", this.cron);
      this.init();
    },
    init() {
      this.cron = "";
      this.tabValue = "秒";
      this.second.cron = "*";
      this.second.select = 0;
      this.second.cycle_start = 1;
      this.second.cycle_end = 2;
      this.second.second_start = 0;
      this.second.second_end = 1;
      this.second.selected = [];

      this.minutes.cron = "*";
      this.minutes.select = 0;
      this.minutes.cycle_start = 1;
      this.minutes.cycle_end = 2;
      this.minutes.second_start = 0;
      this.minutes.second_end = 1;
      this.minutes.selected = [];

      this.hour.cron = "*";
      this.hour.select = 0;
      this.hour.cycle_start = 0;
      this.hour.cycle_end = 2;
      this.hour.second_start = 0;
      this.hour.second_end = 1;
      this.hour.selected = [];

      this.day.cron = "*";
      this.day.select = 0;
      this.day.cycle_start = 1;
      this.day.cycle_end = 2;
      this.day.second_start = 1;
      this.day.second_end = 1;
      this.day.selected = [];
      this.day.near_work_day = 1;

      this.month.cron = "*";
      this.month.select = 0;
      this.month.cycle_start = 1;
      this.month.cycle_end = 2;
      this.month.second_start = 1;
      this.month.second_end = 1;
      this.month.selected = [];

      this.week.cron = "?";
      this.week.select = 0;
      this.week.cycle_start = 1;
      this.week.cycle_end = 2;
      this.week.second_start = 1;
      this.week.second_end = 1;
      this.week.last = 1;
      this.week.selected = [];

      this.year.cron = "";
      this.year.select = 0;
      this.year.cycle_start = 0;
      this.year.cycle_end = 0;

      let date = new Date();
      this.year.cycle_start = date.getFullYear();
      this.year.cycle_end = date.getFullYear() + 1;
      this.year.min = date.getFullYear();
      this.cron = this.cronStr();
    },
    secondChange(select){
        if(select >= 0){
            this.second.select = select;
        }
        switch(parseInt(this.second.select)){
            case 0: 
                this.second.cron = '*';
                break;
            case 1: 
                this.second.cron = this.second.cycle_start+'-'+this.second.cycle_end;
                break;
            case 2: 
                this.second.cron = this.second.second_start + '/' + this.second.second_end;
                break;
            case 3: 
                if(this.second.selected.length == 0){
                    this.second.cron = '?';
                }else{
                    let str = '';
                    this.second.selected.sort((a,b)=>{
                        return a > b;
                    });
                    this.second.selected.forEach((item)=>{
                        str +=item+',';
                    });
                    str = str.substring(0,str.length-1);
                    this.second.cron = str;
                }
                break;
        }
        this.cron = this.cronStr();
    },
    minutesChange(select){
        if(select >= 0){
            this.minutes.select = select;
        }
        switch(parseInt(this.minutes.select)){
            case 0: 
                this.minutes.cron = '*';
                break;
            case 1: 
                this.minutes.cron = this.minutes.cycle_start+'-'+this.minutes.cycle_end;
                break;
            case 2: 
                this.minutes.cron = this.minutes.min_start + '/' + this.minutes.min_end;
                break;
            case 3: 
                if(this.minutes.selected.length == 0){
                    this.minutes.cron = '?';
                }else{
                    let str = '';
                    this.minutes.selected.sort((a,b)=>{
                        return a > b;
                    });
                    this.minutes.selected.forEach((item)=>{
                        str +=item+',';
                    });
                    str = str.substring(0,str.length-1);
                    this.minutes.cron = str;
                }
                break;
        }
        this.cron = this.cronStr();
    },
    hourChange(select){
        if(select >= 0){
            this.hour.select = select;
        }
        switch(parseInt(this.hour.select)){
            case 0: 
                this.hour.cron = '*';
                break;
            case 1: 
                this.hour.cron = this.hour.cycle_start+'-'+this.hour.cycle_end;
                break;
            case 2: 
                this.hour.cron = this.hour.hour_start + '/' + this.hour.hour_end;
                break;
            case 3: 
                if(this.hour.selected.length == 0){
                    this.hour.cron = '?';
                }else{
                    let str = '';
                    this.hour.selected.sort((a,b)=>{
                        return a > b;
                    });
                    this.hour.selected.forEach((item)=>{
                        str +=item+',';
                    });
                    str = str.substring(0,str.length-1);
                    this.hour.cron = str;
                }
                break;
        }
        this.cron = this.cronStr();
    },
    dayChange(select){
        if(select >= 0){
            this.day.select = select;
        }
        switch(parseInt(this.day.select)){
            case 0: 
                this.day.cron = '*';
                break;
            case 1: 
                this.day.cron = '?';
                break;
            case 2: 
                this.day.cron = this.day.cycle_start+'-'+this.day.cycle_end;
                break;
            case 3:
                this.day.cron = this.day.day_start + '/' + this.day.day_end; 
                break;
            case 4: 
                this.day.cron = this.day.near_work_day +'W';
                break;
            case 5: 
                this.day.cron = 'L';
                break;
            case 6: 
                if(this.day.selected.length == 0){
                    this.day.cron = '?';
                }else{
                    let str = '';
                    this.day.selected.sort((a,b)=>{
                        return a > b;
                    });
                    this.day.selected.forEach((item)=>{
                        str +=item+',';
                    });
                    str = str.substring(0,str.length-1);
                    this.day.cron = str;
                }
                break;
        }
        this.cron = this.cronStr();
    },
    monthChange(select){
        if(select >= 0){
            this.month.select = select;
        }
        switch(parseInt(this.month.select)){
            case 0: 
                this.month.cron = '*';
                break;
            case 1: 
                this.month.cron = '?';
                break;
            case 2: 
                this.month.cron = this.month.cycle_start+'-'+this.month.cycle_end;
                break;
            case 3: 
                this.month.cron = this.month.month_start + '/' + this.month.month_end;
                break;
            case 4:
                if(this.month.selected.length == 0){
                    this.month.cron = '?';
                }else{
                    let str = '';
                    this.month.selected.sort((a,b)=>{
                        return a > b;
                    });
                    this.month.selected.forEach((item)=>{
                        str +=item+',';
                    });
                    str = str.substring(0,str.length-1);
                    this.month.cron = str;
                }
                break;
        }
        this.cron = this.cronStr();
    },
    weekChange(select){
        if(select >= 0){
            this.week.select = select;
        }
        switch(parseInt(this.week.select)){
            case 0: 
                this.week.cron = '*';
                break;
            case 1: 
                this.week.cron = '?';
                break;
            case 2: 
                this.week.cron = this.week.cycle_start+'-'+this.week.cycle_end;
                break;
            case 3: 
                this.week.cron = this.week.week_start + '#' + this.week.week_end;
                break;
            case 4:
                this.week.cron = this.week.last+'L';
                break;
            case 5:
                if(this.week.selected.length == 0){
                    this.week.cron = '?';
                }else{
                    let str = '';
                    this.week.selected.sort((a,b)=>{
                        return a > b;
                    });
                    this.week.selected.forEach((item)=>{
                        str +=item+',';
                    });
                    str = str.substring(0,str.length-1);
                    this.week.cron = str;
                }
                break;
        }
        this.cron = this.cronStr();
    },
    yearChange(select){
        if(select >= 0){
            this.year.select = select;
        }
        switch(parseInt(this.year.select)){
            case 0: 
                this.year.cron = '';
                break;
            case 1: 
                this.year.cron = '*';
                break;
            case 2:
                this.year.cron = this.year.cycle_start+'-'+this.year.cycle_end;
                break; 
        }
        this.cron = this.cronStr();
    },
    parseCorn(cron){
        this.cron = cron;
        let temp = cron.split(' ');
        if(temp && temp.length>=6){
            this.second.cron = temp[0];
            this.minutes.cron = temp[1];
            this.hour.cron = temp[2];
            this.day.cron = temp[3];
            this.month.cron = temp[4];
            this.week.cron = temp[5];
            this.year.cron = temp.length==7 ? temp[6] : '';
            //解析秒
            if(temp[0] === '*'){
                this.second.select = 0;
            } else if(temp[0].indexOf('-') >= 0){
                this.second.select = 1;
                let t = temp[0].split('-');
                this.second.cycle_start = parseInt(t[0]);
                this.second.cycle_end = parseInt(t[1]);
            } else if(temp[0].indexOf('/') >= 0){
                this.second.select = 2;
                let t = temp[0].split('/');
                this.second.second_start = parseInt(t[0]);
                this.second.second_end = parseInt(t[1]);
            } else {
                this.second.select = 3;
                if(temp[0] != '?'){
                    let t = temp[0].split(',');
                    let tempInt = [];
                    for(let i=0 ;i<t.length ;i++){
                        tempInt[i] = parseInt(t[i]);
                    }
                    this.second.selected = tempInt;
                }
            }
            //解析分钟
            if(temp[1] === '*'){
                this.minutes.select = 0;
            } else if(temp[1].indexOf('-') >= 0){
                this.minutes.select = 1;
                let t = temp[1].split('-');
                this.minutes.cycle_start = parseInt(t[0]);
                this.minutes.cycle_end = parseInt(t[1]);
            } else if(temp[1].indexOf('/') >= 0){
                this.minutes.select = 2;
                let t = temp[1].split('/');
                this.minutes.min_start = parseInt(t[0]);
                this.minutes.min_end = parseInt(t[1]);
            } else {
                this.minutes.select = 3;
                if(temp[1] != '?'){
                    let t = temp[1].split(',');
                    let tempInt = [];
                    for(let i=0 ;i<t.length ;i++){
                        tempInt[i] = parseInt(t[i]);
                    }
                    this.minutes.selected = tempInt;
                }
            }
            //解析小时
            if(temp[2] === '*'){
                this.hour.select = 0;
            } else if(temp[2].indexOf('-') >= 0){
                this.hour.select = 1;
                let t = temp[2].split('-');
                this.hour.cycle_start = parseInt(t[0]);
                this.hour.cycle_end = parseInt(t[1]);
            } else if(temp[2].indexOf('/') >= 0){
                this.hour.select = 2;
                let t = temp[2].split('/');
                this.hour.hour_start = parseInt(t[0]);
                this.hour.hour_end = parseInt(t[1]);
            } else {
                this.hour.select = 3;
                if(temp[2] != '?'){
                    let t = temp[2].split(',');
                    let tempInt = [];
                    for(let i=0 ;i<t.length ;i++){
                        tempInt[i] = parseInt(t[i]);
                    }
                    this.hour.selected = tempInt;
                }
            }
            //解析 日
            if(temp[3] === '*'){
                this.day.select = 0;
            } else if(temp[3].indexOf('-') >= 0){
                this.day.select = 1;
                let t = temp[3].split('-');
                this.day.cycle_start = parseInt(t[0]);
                this.day.cycle_end = parseInt(t[1]);
            } else if(temp[3].indexOf('/') >= 0){
                this.day.select = 2;
                let t = temp[3].split('/');
                this.day.day_start = parseInt(t[0]);
                this.day.day_end = parseInt(t[1]);
            } else if(temp[3].indexOf('W')>=0){
                this.day.select = 4;
                this.day.near_work_day = parseInt(temp[3].substring(0,1));
            } else if(temp[3] === 'L'){
                this.day.select = 5;
            } else {
                this.day.select = 1;
                if(temp[3] != '?'){
                    this.day.select = 6;
                    let t = temp[3].split(',');
                    let tempInt = [];
                    for(let i=0 ;i<t.length ;i++){
                        tempInt[i] = parseInt(t[i]);
                    }
                    this.day.selected = tempInt;
                }
            }
            //解析 月
            if(temp[4] === '*'){
                this.month.select = 0;
            } else if(temp[4].indexOf('-') >= 0){
                this.month.select = 2;
                let t = temp[4].split('-');
                this.month.cycle_start = parseInt(t[0]);
                this.month.cycle_end = parseInt(t[1]);
            } else if(temp[4].indexOf('/') >= 0){
                this.month.select = 3;
                let t = temp[4].split('/');
                this.month.month_start = parseInt(t[0]);
                this.month.month_end = parseInt(t[1]);
            } else {
                this.month.select = 1;
                if(temp[4] != '?'){
                    this.month.select = 4;
                    let t = temp[4].split(',');
                    let tempInt = [];
                    for(let i=0 ;i<t.length ;i++){
                        tempInt[i] = parseInt(t[i]);
                    }
                    this.month.selected = tempInt;
                }
            }
            //解析 周
            if(temp[5] === '*'){
                this.week.select = 0;
            } else if(temp[5].indexOf('/') >= 0){
                this.week.select = 2;
                let t = temp[5].split('/');
                this.week.cycle_start = parseInt(t[0]);
                this.week.cycle_end = parseInt(t[1]);
            } else if(temp[5].indexOf('#') >= 0){
                this.week.select = 3;
                let t = temp[5].split('#');
                this.week.week_start = parseInt(t[0]);
                this.week.week_end = parseInt(t[1]);
            } else if(temp[5].indexOf('L') >= 0){
                this.week.select = 4;
                this.week.last = parseInt(temp[5].substring(0,1));
            } else {
                this.week.select = 1;
                if(temp[5] != '?'){
                    this.week.select = 5;
                    let t = temp[5].split(',');
                    let tempInt = [];
                    for(let i=0 ;i<t.length ;i++){
                        tempInt[i] = parseInt(t[i]);
                    }
                    this.week.selected = tempInt;
                }
            }
            if(temp.length == 7){
                if(temp[6] == '*'){
                    this.year.select = 1;
                }else{
                    this.year.select = 2;
                    let t = temp[6].split('-');
                    this.year.cycle_start = parseInt(t[0]);
                    this.year.cycle_end = parseInt(t[1]);
                }
            }else{
                this.year.cron = '';
            }

        }else{
            util.error('表达式错误！');
        }
    }
  },
  created() {
    this.init();
    this.hour.list.push(0);
    for (let i = 1; i <= 59; i++) {
        this.second.list.push(i);
        this.minutes.list.push(i);
        if (i <= 7) {
          this.week.list.push(i);
        }
        if (i <= 12) {
          this.month.list.push(i);
        }
        if (i <= 23) {
          this.hour.list.push(i);
        }
        if (i <= 31) {
          this.day.list.push(i);
        }
    }
    this.$watch('currentCron',function(newVal,oldVal){
        this.parseCorn(newVal);
    });
    this.$watch('cron',function(newVal,oldVal){
        this.parseCorn(newVal);
    });
  }
};
</script>