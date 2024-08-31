function changeStatus(el) {

    const buttonEl = document.getElementById(el.name); //select 엘리먼트의 이름으로 해당 버튼 찾아

    buttonEl.dataset.status = el.value;
}
function sendStatus(el) {
    const formData = new FormData();

    formData.append("orderNo", el.dataset.orderno);
    formData.append("status", el.dataset.status);

    url = el.dataset.url;
    console.log(url);
    console.log(el.dataset.orderno);
    console.log(el.dataset.status);

    //console.log(formData);

    const {ajaxLoad} = commonLib;
    ajaxLoad(url, 'POST', formData, null, 'json')
        .then(res => {

            console.log(res);

            if (!res.success) {
                alert("res.message :" + res.message );
            }else {
                alert(res.message);
            }

        })
        .catch(err => alert(err.message));


}

    /*
    const formData = new FormData();
    formData.append("memberSeq", el.dataset.member);
    formData.append("authorityName", el.value);
    formData.append("invoke", el.checked);

    const {ajaxLoad} = commonLib;
    ajaxLoad('/reservation/admin/update', 'POST', formData, null, 'json')
        .then(res => {

            console.log(res);
            //alert(res.data);

            if (!res.success) {
                alert(res.message);
            }

        })
        .catch(err => alert(err.message));

     */



