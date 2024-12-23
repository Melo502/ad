(function() {
    // 自执行函数，防止全局命名冲突

    // 动态加载 jQuery 和 Bootstrap 资源
    function loadResources() {
        return new Promise((resolve, reject) => {
            // 创建 jQuery 脚本标签
            const jqueryScript = document.createElement('script');
            jqueryScript.src = 'https://code.jquery.com/jquery-3.6.0.min.js';
            jqueryScript.onload = () => {
                console.log('jQuery 已加载');
                // 创建 Bootstrap 样式标签
                const bootstrapCss = document.createElement('link');
                bootstrapCss.rel = 'stylesheet';
                bootstrapCss.href = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css';
                document.head.appendChild(bootstrapCss);

                // 创建 Bootstrap 脚本标签
                const bootstrapScript = document.createElement('script');
                bootstrapScript.src = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js';
                bootstrapScript.onload = () => {
                    console.log('Bootstrap 已加载');
                    resolve();  // 所有资源加载完成后执行 resolve
                };
                bootstrapScript.onerror = reject;
                document.body.appendChild(bootstrapScript);
            };
            jqueryScript.onerror = reject;  // 处理加载失败
            document.body.appendChild(jqueryScript);
        });
    }

    // 确保加载了 jQuery 和 Bootstrap 后再执行广告展示逻辑
    loadResources().then(() => {
        // 获取当前执行的 <script> 标签
        const currentScript = document.getElementById('adScript');
        if (!currentScript) {
            console.error('无法获取当前脚本');
            displayError('无法获取当前脚本，请重试。');
            return;
        }

        // 获取脚本的 src 属性
        const scriptSrc = currentScript.src;
        if (!scriptSrc) {
            console.error('脚本未包含 src 属性');
            displayError('脚本未正确加载，请重试。');
            return;
        }

        // 解析 src 中的查询参数，获取 adIds
        let adIds;
        try {
            const url = new URL(scriptSrc, window.location.origin); // 确保解析时基于当前页面域名
            adIds = url.searchParams.get('adIds');
        } catch (e) {
            console.error('解析脚本 URL 失败:', e);
            displayError('脚本 URL 格式不正确。');
            return;
        }

        if (adIds) {
            console.log('广告ID字符串:', adIds);

            // 构建 API 请求 URL（POST 方法）
            const apiUrl = '\n' +
                'http://localhost:8080/advertise/advertiserServlet.do?method=getAdContent';

            // 发送广告ID数组作为请求体的 JSON
            fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ adIds })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('网络响应错误: ' + response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('API 返回的数据:', data);
                    displayAds(data);  // 调用显示广告的函数
                })
                .catch(err => {
                    console.error('广告加载失败:', err);
                    displayError('广告加载失败，请稍后重试。');
                });
        } else {
            console.error('广告ID丢失');
            displayError('无法获取广告ID，请重试。');
        }

    }).catch(err => {
        console.error('资源加载失败:', err);
        displayError('广告资源加载失败，请稍后重试。');
    });


    /**
     * 显示广告的函数
     * @param {Array} adDataArray - 广告数据数组
     */
    function displayAds(adDataArray) {
        const carouselContainer = document.createElement('div');
        carouselContainer.id = 'bannerCarousel';
        carouselContainer.classList.add('carousel', 'slide', 'banner-carousel');
        carouselContainer.setAttribute('data-bs-ride', 'carousel');

        const indicators = document.createElement('div');
        indicators.classList.add('carousel-indicators');

        const carouselInner = document.createElement('div');
        carouselInner.classList.add('carousel-inner');

        if (!Array.isArray(adDataArray)) {
            console.warn('广告数据不是数组，正在转换为数组');
            adDataArray = [adDataArray];
        }

        if (adDataArray.length === 0) {
            console.warn('没有可显示的广告。');
            displayError('没有可显示的广告。');
            return;
        }

        adDataArray.forEach((adData, index) => {
            if (!adData.imageUrl || !adData.url) {
                console.warn(`广告项缺少 imageUrl 或 url:`, adData);
                return; // 跳过此广告项
            }

            const carouselItem = document.createElement('div');
            carouselItem.classList.add('carousel-item');
            if (index === 0) {
                carouselItem.classList.add('active');
            }

            const adLink = document.createElement('a');
            adLink.href = sanitizeUrl(adData.url);
            adLink.target = "_blank";
            adLink.innerHTML = `
                <img src="${sanitizeUrl(adData.imageUrl)}" class="d-block w-100" alt="${sanitizeText(adData.title)}">
            `;

            // 为每个广告项添加点击事件监听
            adLink.addEventListener('click', function(event) {
                event.preventDefault();  // 防止页面跳转，先发送点击数据
                logAdClick(adData.id);  // 记录广告点击
                window.open(adData.url, "_blank");  // 最终打开广告链接
            });

            carouselItem.appendChild(adLink);

            const carouselCaption = document.createElement('div');
            carouselCaption.classList.add('carousel-caption', 'd-none', 'd-md-block');
            carouselCaption.innerHTML = `
                <h5>${sanitizeText(adData.title)}</h5>
                <p>${sanitizeText(adData.description)}</p>
            `;
            carouselItem.appendChild(carouselCaption);

            carouselInner.appendChild(carouselItem);

            const indicator = document.createElement('button');
            indicator.type = 'button';
            indicator.setAttribute('data-bs-target', '#bannerCarousel');
            indicator.setAttribute('data-bs-slide-to', index);
            indicator.setAttribute('aria-label', `Slide ${index + 1}`);
            if (index === 0) {
                indicator.classList.add('active');
                indicator.setAttribute('aria-current', 'true');
            }
            indicators.appendChild(indicator);
        });

        carouselContainer.appendChild(indicators);
        carouselContainer.appendChild(carouselInner);

        const prevButton = document.createElement('button');
        prevButton.classList.add('carousel-control-prev');
        prevButton.type = 'button';
        prevButton.setAttribute('data-bs-target', '#bannerCarousel');
        prevButton.setAttribute('data-bs-slide', 'prev');
        prevButton.innerHTML = `
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">上一张</span>
        `;

        const nextButton = document.createElement('button');
        nextButton.classList.add('carousel-control-next');
        nextButton.type = 'button';
        nextButton.setAttribute('data-bs-target', '#bannerCarousel');
        nextButton.setAttribute('data-bs-slide', 'next');
        nextButton.innerHTML = `
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">下一张</span>
        `;

        carouselContainer.appendChild(prevButton);
        carouselContainer.appendChild(nextButton);

        const targetElement = document.querySelector('#ad-container');
        if (targetElement) {
            targetElement.appendChild(carouselContainer);
        } else {
            console.error('无法找到广告容器元素');
            displayError('无法找到广告容器元素，请重试。');
        }

        if (typeof bootstrap !== 'undefined' && bootstrap.Carousel) {
            try {
                const carousel = new bootstrap.Carousel(carouselContainer, {
                    interval: 3000,
                    ride: 'carousel'
                });
                console.log('Bootstrap 轮播已初始化');
            } catch (e) {
                console.error('初始化 Bootstrap 轮播失败:', e);
                displayError('轮播功能初始化失败。');
            }
        } else {
            console.error('Bootstrap 未加载或版本不兼容');
            displayError('轮播功能不可用，请确保 Bootstrap 已正确加载。');
        }
    }

    /**
     * 记录广告点击事件
     * @param {string} adId - 广告ID
     */
    function logAdClick(adId) {
        fetch(`
http://localhost:8080/advertise/advertiserServlet.do?method=logAdClick`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ adId })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('点击记录失败');
                }
                console.log(`广告 ${adId} 点击已记录`);
            })
            .catch(err => {
                console.error('记录广告点击失败:', err);
            });
    }

    /**
     * 显示错误消息的辅助函数
     * @param {string} message - 错误消息
     */
    function displayError(message) {
        // 防止重复插入错误消息
        if (document.querySelector('.alert.alert-danger')) return;

        const errorContainer = document.createElement('div');
        errorContainer.classList.add('alert', 'alert-danger', 'mx-auto', 'mt-3');
        errorContainer.style.maxWidth = '800px'; // 设置最大宽度，根据需要调整
        errorContainer.textContent = message;
        document.body.insertAdjacentElement('afterbegin', errorContainer);
    }

    /**
     * 消毒文本内容，防止 XSS
     * @param {string} str - 要消毒的字符串
     * @returns {string} - 消毒后的字符串
     */
    function sanitizeText(str) {
        const temp = document.createElement('div');
        temp.textContent = str;
        return temp.innerHTML;
    }

    /**
     * 消毒 URL，确保其有效性
     * @param {string} url - 要消毒的 URL
     * @returns {string} - 消毒后的 URL
     */
    function sanitizeUrl(url) {
        try {
            const parsedUrl = new URL(url, window.location.origin);
            return parsedUrl.href;
        } catch (e) {
            console.error('无效的 URL:', url);
            return '#';
        }
    }

    /** 添加 CSS 样式到页面 */
    function injectStyles() {
        const style = document.createElement('style');
        style.type = 'text/css';
        style.innerHTML = `
            /* 自定义轮播样式 */
            .banner-carousel {
                position: relative;
                margin: 0 auto; /* 上下间距 0，左右自动居中 */
                max-width: 1000px; /* 最大宽度 */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影 */
                border-radius: 10px; /* 圆角 */
                overflow: hidden; /* 防止内容溢出 */
            }
            .banner-carousel.carousel-item img {
                width: 100%;
                height: 100%; /* 默认高度 */
                object-fit: cover;
                border-radius: 10px; /* 与容器圆角一致 */
            }
            /* 响应式设计 */
            @media (max-width: 2000px) {
                .banner-carousel .carousel-item img {
                    height: 100%;
                }
            }
            @media (min-width: 2000px) {
                .banner-carousel .carousel-item img {
                    height: 100%;
                }
            }
        `;
        document.head.appendChild(style);
    }

    // 调用注入 CSS 方法
    injectStyles();
})();
