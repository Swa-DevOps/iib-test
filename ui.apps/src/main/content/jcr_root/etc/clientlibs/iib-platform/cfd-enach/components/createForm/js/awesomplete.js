// Awesomplete - Lea Verou - MIT license
!function(){var t=function(e,i){var s=this;t.count=(t.count||0)+1,this.count=t.count,this.isOpened=!1,this.input=n(e),this.input.setAttribute("autocomplete","off"),this.input.setAttribute("aria-expanded","false"),this.input.setAttribute("aria-owns","awesomplete_list_"+this.count),this.input.setAttribute("role","combobox"),this.options=i=i||{},function(t,e,i){for(var n in e){var s=e[n],r=t.input.getAttribute("data-"+n.toLowerCase());"number"==typeof s?t[n]=parseInt(r):!1===s?t[n]=null!==r:s instanceof Function?t[n]=null:t[n]=r,t[n]||0===t[n]||(t[n]=n in i?i[n]:s)}}(this,{minChars:2,maxItems:10,autoFirst:!1,data:t.DATA,filter:t.FILTER_CONTAINS,sort:!1!==i.sort&&t.SORT_BYLENGTH,container:t.CONTAINER,item:t.ITEM,replace:t.REPLACE,tabSelect:!1},i),this.index=-1,this.container=this.container(e),this.ul=n.create("ul",{hidden:"hidden",role:"listbox",id:"awesomplete_list_"+this.count,inside:this.container}),this.status=n.create("span",{className:"visually-hidden",role:"status","aria-live":"assertive","aria-atomic":!0,inside:this.container,textContent:0!=this.minChars?"Type "+this.minChars+" or more characters for results.":"Begin typing for results."}),this._events={input:{input:this.evaluate.bind(this),blur:this.close.bind(this,{reason:"blur"}),keydown:function(t){var e=t.keyCode;s.opened&&(13===e&&s.selected?(t.preventDefault(),s.select(void 0,void 0,t)):9===e&&s.selected&&s.tabSelect?s.select(void 0,void 0,t):27===e?s.close({reason:"esc"}):38!==e&&40!==e||(t.preventDefault(),s[38===e?"previous":"next"]()))}},form:{submit:this.close.bind(this,{reason:"submit"})},ul:{mousedown:function(t){t.preventDefault()},click:function(t){var e=t.target;if(e!==this){for(;e&&!/li/i.test(e.nodeName);)e=e.parentNode;e&&0===t.button&&(t.preventDefault(),s.select(e,t.target,t))}}}},n.bind(this.input,this._events.input),n.bind(this.input.form,this._events.form),n.bind(this.ul,this._events.ul),this.input.hasAttribute("list")?(this.list="#"+this.input.getAttribute("list"),this.input.removeAttribute("list")):this.list=this.input.getAttribute("data-list")||i.list||[],t.all.push(this)};function e(t){var e=Array.isArray(t)?{label:t[0],value:t[1]}:"object"==typeof t&&"label"in t&&"value"in t?t:{label:t,value:t};this.label=e.label||e.value,this.value=e.value}t.prototype={set list(t){if(Array.isArray(t))this._list=t;else if("string"==typeof t&&t.indexOf(",")>-1)this._list=t.split(/\s*,\s*/);else if((t=n(t))&&t.children){var e=[];i.apply(t.children).forEach(function(t){if(!t.disabled){var i=t.textContent.trim(),n=t.value||i,s=t.label||i;""!==n&&e.push({label:s,value:n})}}),this._list=e}document.activeElement===this.input&&this.evaluate()},get selected(){return this.index>-1},get opened(){return this.isOpened},close:function(t){this.opened&&(this.input.setAttribute("aria-expanded","false"),this.ul.setAttribute("hidden",""),this.isOpened=!1,this.index=-1,"nomatches"!=t.reason&&this.status.setAttribute("hidden",""),n.fire(this.input,"awesomplete-close",t||{}))},open:function(){this.input.setAttribute("aria-expanded","true"),this.ul.removeAttribute("hidden"),this.isOpened=!0,this.status.removeAttribute("hidden"),this.autoFirst&&-1===this.index&&this.goto(0),n.fire(this.input,"awesomplete-open")},destroy:function(){if(n.unbind(this.input,this._events.input),n.unbind(this.input.form,this._events.form),!this.options.container){var e=this.container.parentNode;e.insertBefore(this.input,this.container),e.removeChild(this.container)}this.input.removeAttribute("autocomplete"),this.input.removeAttribute("aria-autocomplete");var i=t.all.indexOf(this);-1!==i&&t.all.splice(i,1)},next:function(){var t=this.ul.children.length;this.goto(this.index<t-1?this.index+1:t?0:-1)},previous:function(){var t=this.ul.children.length,e=this.index-1;this.goto(this.selected&&-1!==e?e:t-1)},goto:function(t){var e=this.ul.children;this.selected&&e[this.index].setAttribute("aria-selected","false"),this.index=t,t>-1&&e.length>0&&(e[t].setAttribute("aria-selected","true"),this.status.textContent=e[t].textContent+", list item "+(t+1)+" of "+e.length,this.input.setAttribute("aria-activedescendant",this.ul.id+"_item_"+this.index),this.ul.scrollTop=e[t].offsetTop-this.ul.clientHeight+e[t].clientHeight,n.fire(this.input,"awesomplete-highlight",{text:this.suggestions[this.index]}))},select:function(t,e,i){if(t?this.index=n.siblingIndex(t):t=this.ul.children[this.index],t){var s=this.suggestions[this.index];n.fire(this.input,"awesomplete-select",{text:s,origin:e||t,originalEvent:i})&&(this.replace(s),this.close({reason:"select"}),n.fire(this.input,"awesomplete-selectcomplete",{text:s,originalEvent:i}))}},evaluate:function(){var t=this,i=this.input.value;i.length>=this.minChars&&this._list&&this._list.length>0?(this.index=-1,this.ul.innerHTML="",this.suggestions=this._list.map(function(n){return new e(t.data(n,i))}).filter(function(e){return t.filter(e,i)}),!1!==this.sort&&(this.suggestions=this.suggestions.sort(this.sort)),this.suggestions=this.suggestions.slice(0,this.maxItems),this.suggestions.forEach(function(e,n){t.ul.appendChild(t.item(e,i,n))}),0===this.ul.children.length?(this.status.textContent="No results found",this.close({reason:"nomatches"})):(this.open(),this.status.textContent=this.ul.children.length+" results found")):(this.close({reason:"nomincharacters"}),this.status.textContent="No results found")}},t.all=[],t.FILTER_CONTAINS=function(t,e){return RegExp(n.regExpEscape(e.trim()),"i").test(t)},t.FILTER_STARTSWITH=function(t,e){return RegExp("^"+n.regExpEscape(e.trim()),"i").test(t)},t.SORT_BYLENGTH=function(t,e){return t.length!==e.length?t.length-e.length:t<e?-1:1},t.CONTAINER=function(t){return n.create("div",{className:"awesomplete",around:t})},t.ITEM=function(t,e,i){var s=""===e.trim()?t:t.replace(RegExp(n.regExpEscape(e.trim()),"gi"),"<mark>$&</mark>");return n.create("li",{innerHTML:s,role:"option","aria-selected":"false",id:"awesomplete_list_"+this.count+"_item_"+i})},t.REPLACE=function(t){this.input.value=t.value},t.DATA=function(t){return t},Object.defineProperty(e.prototype=Object.create(String.prototype),"length",{get:function(){return this.label.length}}),e.prototype.toString=e.prototype.valueOf=function(){return""+this.label};var i=Array.prototype.slice;function n(t,e){return"string"==typeof t?(e||document).querySelector(t):t||null}function s(t,e){return i.call((e||document).querySelectorAll(t))}function r(){s("input.awesomplete").forEach(function(e){new t(e)})}n.create=function(t,e){var i=document.createElement(t);for(var s in e){var r=e[s];if("inside"===s)n(r).appendChild(i);else if("around"===s){var o=n(r);o.parentNode.insertBefore(i,o),i.appendChild(o),null!=o.getAttribute("autofocus")&&o.focus()}else s in i?i[s]=r:i.setAttribute(s,r)}return i},n.bind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.addEventListener(e,n)})}},n.unbind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.removeEventListener(e,n)})}},n.fire=function(t,e,i){var n=document.createEvent("HTMLEvents");for(var s in n.initEvent(e,!0,!0),i)n[s]=i[s];return t.dispatchEvent(n)},n.regExpEscape=function(t){return t.replace(/[-\\^$*+?.()|[\]{}]/g,"\\$&")},n.siblingIndex=function(t){for(var e=0;t=t.previousElementSibling;e++);return e},"undefined"!=typeof self&&(self.Awesomplete=t),"undefined"!=typeof Document&&("loading"!==document.readyState?r():document.addEventListener("DOMContentLoaded",r)),t.$=n,t.$$=s,"object"==typeof module&&module.exports&&(module.exports=t)}();