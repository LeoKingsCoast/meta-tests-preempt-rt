SUMMARY = "Toggle on/off the GPIO0_41 pin of a AM62x TI SoC"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://main.c"

TARGET_CC_ARCH += "${LDFLAGS}"

S = "${WORKDIR}"

do_compile() {
    ${CC} ${CFLAGS} -Wall -Werror -o gpio-toggle
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gpio-toggle ${D}${bindir}
}
