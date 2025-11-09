SUMMARY = "Customized Test Scripts for RT Evaluation"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=177700f199f112336089ba22aca40c82"

SRC_URI = "git://github.com/LeoKingsCoast/rt-test-scripts.git;branch=main;protocol=https"
SRCREV = "${AUTOREV}"
PV = "1.0+git"

S = "${WORKDIR}/git"

RDEPENDS:${PN} = "rt-tests gnuplot stress-ng"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}/etc
    install -m 0755 run-benchmark ${D}${bindir}
    install -m 0755 run-stress ${D}${bindir}
    install -m 0755 gen-hist ${D}${bindir}
    install -m 0755 plot-settings ${D}/etc/
}
