SUMMARY = "Convolution Reverb"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=177700f199f112336089ba22aca40c82"

SRC_URI = "git://github.com/LeoKingsCoast/convolution-reverb.git;branch=main;protocol=https"
SRCREV = "${AUTOREV}"
PV = "1.0+git"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "LDFLAGS='${LDFLAGS}'"

DEPENDS = "portaudio-v19 fftw"

do_install() {
    install -d ${D}${bindir}
    install build/conv-rev ${D}${bindir}/conv-rev
}

FILES_${PN} += "${bindir}/conv-rev"
