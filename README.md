# novel-parent
本项目源于学习爬虫技术，后用dubbo框架进行了重构，由于时间紧迫，项目比较粗糙，后期将进一步完善。该项目主要从目标网站爬取内容存于hadoop或mysql中。项目中的爬虫技术借鉴了别人的项目，在此表示感谢。
启动方式：运行每一个模块下test包下的Service。
启动顺序: novel-common、novel-update、novel-crawl、novel-intro、novel-read。
novel-store、novel-mq、novel-consumer等服务不需要启动。

