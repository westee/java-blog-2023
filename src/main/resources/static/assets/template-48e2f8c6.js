import{_ as u,a as d,b as n,e as r,f as e,t as o,F as c,n as h,c as g,p as m,q as _,i as f}from"./index-433ea9bd.js";/* empty css                                                           *//* empty css                 */import{b as i}from"./blog-b243a7f5.js";const y={data(){return{blogs:[],user:{},page:1,total:0}},created(){this.userId=this.$route.params.userId,this.page=this.$route.query.page||1,i.getBlogsByUserId(this.userId,{page:this.page}).then(t=>{console.log(t),this.page=t.page,this.total=t.total,this.blogs=t.data,t.data.length>0&&(this.user=t.data[0].user)})},methods:{onPageChange(t){i.getBlogsByUserId(this.userId,{page:t}).then(a=>{console.log(a),this.blogs=a.data,this.total=a.total,this.page=a.page,this.$router.push({path:`/user/${this.userId}`,query:{page:t}})})},splitDate(t){let a=typeof t=="object"?t:new Date(t);return{date:a.getDate(),month:a.getMonth()+1,year:a.getFullYear()}}}},$={id:"user"},B={class:"user-info"},C=["src","alt"],D={class:"date"},I={class:"day"},k={class:"month"},v={class:"year"},b={class:"pagination"};function q(t,a,A,F,j,E){const l=d("router-link"),p=m;return n(),r("div",$,[e("section",B,[e("img",{src:t.user.avatar,alt:t.user.username,class:"avatar"},null,8,C),e("h3",null,o(t.user.username),1)]),e("section",null,[(n(!0),r(c,null,h(t.blogs,s=>(n(),_(l,{class:"item",key:s.id,to:`/detail/${s.id}`},{default:f(()=>[e("div",D,[e("span",I,o(t.splitDate(s.createdAt).date),1),e("span",k,o(t.splitDate(s.createdAt).month)+"月",1),e("span",v,o(t.splitDate(s.createdAt).year),1)]),e("h3",null,o(s.title),1),e("p",null,o(s.description),1)]),_:2},1032,["to"]))),128))]),e("section",b,[g(p,{layout:"prev, pager, next",total:t.total,"current-page":t.page,onCurrentChange:t.onPageChange},null,8,["total","current-page","onCurrentChange"])])])}const w=u(y,[["render",q]]);export{w as default};
