/*
window.addEventListener("DOMContentLoaded", function() {
    const csrfToken = document.querySelector("meta[name='csrf_token']")?.content?.trim();
    const csrfHeader = document.querySelector("meta[name='csrf_header']")?.content?.trim();
    let rootUrl = document.querySelector("meta[name='rootUrl']")?.content?.trim() ?? '';

    rootUrl = rootUrl === '/' ? '' : rootUrl;
    let url = "/member/authorities/save"
    url = location.protocol + "//" + location.host + rootUrl + url;

    const authEl = document.getElementsByClassName("authorities");
    for(const el of authEl)  {
        el.addEventListener("click", function () {

            const formData = new FormData();
            formData.append("memberSeq", el.dataset.member);
            formData.append("authorityName", el.value);
            formData.append("isTrue", el.checked);

            let headers;

            if (csrfHeader && csrfToken) {
                headers = headers ?? {};
                headers[csrfHeader] = csrfToken;
            }
            const options = {
                method: "POST",
            };
            options.body = formData;
            options.headers = headers;

            return new Promise((resolve, reject) => {
                fetch(url, options)
                    .then(res => responseType === 'text' ? res.text() : res.json()) // res.json() - JSON / res.text() - 텍스트
                    .then(data => resolve(data))
                    .catch(err => reject(err));
            });
/*
            const { ajaxLoad } = commonLib;
            ajaxLoad('/member/authorities/save', 'POST', formData)
                .then(res => {
                    if (!res.success) {
                        alert(res.message);
                    }

                })
                .catch(err => alert(err.message));
*
        });
    }
});

 */