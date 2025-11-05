SUMMARY = "Toggle on/off the GPIO0_41 pin of a AM62x TI SoC"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=177700f199f112336089ba22aca40c82"

SRC_URI = "git://github.com/LeoKingsCoast/memory-access-gpio.git;branch=main;protocol=https"
SRCREV = "${AUTOREV}"
PV = "1.0+git"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gpio-toggle ${D}${bindir}
}
