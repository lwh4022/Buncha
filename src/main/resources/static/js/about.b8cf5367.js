(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["about"],{f820:function(t,a,s){"use strict";s.r(a);var n=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("v-container",{staticClass:"pa-0",attrs:{fluid:""}},[s("header-bar",{attrs:{windowXSize:t.windowXSize}}),s("v-card",{staticClass:"mx-auto mt-12",staticStyle:{width:"1024px"}},[s("div",{staticClass:"headline font-weight-bold py-12 pl-5"},[t._v("자주 묻는 질문")]),s("div",{staticClass:"px-5 pb-5"},[s("v-expansion-panels",{attrs:{popout:""}},[s("v-expansion-panel",{staticClass:"mb-2"},[s("v-expansion-panel-header",[s("span",[s("v-icon",{staticClass:"text--white",staticStyle:{color:"#555900"}},[t._v("fas fa-question-circle")]),t._v("\n                 업무등록은 어떻게 하나요?\n            ")],1)]),s("v-expansion-panel-content",{staticClass:"pt-4"},[t._v("\n            업무 등록은 홈페이지를 통해서 가능하며, 등록 후 심사를 거쳐 홈페이지에 게시됩니다.\n          ")])],1),s("v-expansion-panel",{staticClass:"mb-2"},[s("v-expansion-panel-header",[s("span",[s("v-icon",{staticClass:"text--white",staticStyle:{color:"#555900"}},[t._v("fas fa-question-circle")]),t._v("\n                 프리랜서 등록 후 결과는 언제쯤 받아 볼 수 있을까요?\n            ")],1)]),s("v-expansion-panel-content",{staticClass:"pt-4"},[t._v("\n            내부 검토 후 답변까지 보통 1 ~ 2일 정도 소요가 됩니다. 최대한 빨리 답변 드릴 수 있도록 최선을 다하겠습니다.\n          ")])],1),s("v-expansion-panel",{staticClass:"mb-2"},[s("v-expansion-panel-header",[s("span",[s("v-icon",{staticClass:"text--white",staticStyle:{color:"#555900"}},[t._v("fas fa-question-circle")]),t._v("\n                 파트너와 협의 시 어떤 도움을 받을 수 있을까요?\n            ")],1)]),s("v-expansion-panel-content",{staticClass:"pt-4"},[t._v("\n            아직까지 미팅 및 협의 시에 제공할 수 있는 서비스는 없습니다.\n          ")])],1),s("v-expansion-panel",{staticClass:"mb-2"},[s("v-expansion-panel-header",[s("span",[s("v-icon",{staticClass:"text--white",staticStyle:{color:"#555900"}},[t._v("fas fa-question-circle")]),t._v("\n                 대금 지급과 관련 서비스가 있을까요?\n            ")],1)]),s("v-expansion-panel-content",{staticClass:"pt-4"},[t._v("\n            아직까지 대금 결제와 관련된 서비스가 없습니다.\n          ")])],1),s("v-expansion-panel",{staticClass:"mb-2"},[s("v-expansion-panel-header",[s("span",[s("v-icon",{staticClass:"text--white",staticStyle:{color:"#555900"}},[t._v("fas fa-question-circle")]),t._v("\n                 회원 탈퇴 방법을 알고 싶습니다.\n            ")],1)]),s("v-expansion-panel-content",{staticClass:"pt-4"},[t._v("\n            관리자에게 메일을 보내주시면 회원 탈퇴를 진행해 드리도록 하겠습니다.\n          ")])],1)],1)],1)]),s("v-card",{staticClass:"mx-auto mt-12",staticStyle:{width:"1024px",height:"350px"}},[s("v-row",[s("v-col",{attrs:{cols:"6"}},[s("div",{staticClass:"headline font-weight-bold pa-4"},[t._v("도움이 더 필요하신가요?")]),s("div",{staticClass:"pl-4"},[t._v("메일로 당신의 문의사항을 보내주세요")])]),s("v-col",{attrs:{cols:"6"}},[s("v-card-text",[s("v-textarea",{attrs:{height:"100%",label:"문의사항을 입력해주세요",filled:"",outlined:""},model:{value:t.contents,callback:function(a){t.contents=a},expression:"contents"}}),s("v-text-field",{attrs:{label:"답변 받으실 이메일을 적어주세요",rules:[t.rules.emailCheck],filled:"",outlined:""},model:{value:t.email,callback:function(a){t.email=a},expression:"email"}})],1),s("v-card-actions",[s("v-btn",{staticClass:"ml-auto",attrs:{color:"info"},on:{click:t.submit}},[t._v("전송")]),s("a",{ref:"mailTo",staticClass:"d-none",attrs:{href:t.mailTo}})],1)],1)],1)],1)],1)},e=[],i=s("c279"),l={props:["windowXSize"],data:function(){return{rules:{emailCheck:function(t){var a=/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;return a.test(t)||"이메일 형식이 아닙니다"}},contents:"",email:"",mailTo:""}},components:{"header-bar":i["a"]},computed:{form:function(){return{contents:this.contents,email:this.email}}},methods:{submit:function(){var t="고객이 보내온 이야기",a=this.contents,s=this.email;this.mailTo="mailto:lwh4022@gmail.com&subject="+t+"&body="+a+"&20"+s,this.$refs.mailTo.click()}}},o=l,c=s("2877"),p=Object(c["a"])(o,n,e,!1,null,null,null);a["default"]=p.exports}}]);
//# sourceMappingURL=about.b8cf5367.js.map