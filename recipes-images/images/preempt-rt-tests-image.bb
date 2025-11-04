SUMMARY = "Real-Time Image Tests Image"
DESCRIPTION = "Image with packages and custom applications for testing Real-Time capabilities"

LICENSE = "MIT"

inherit core-image

export IMAGE_BASENAME = "Real-Time-Test-Image"
MACHINE_NAME ?= "${MACHINE}"
IMAGE_NAME = "${MACHINE_NAME}_${IMAGE_BASENAME}"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

IMAGE_LINGUAS = "en-us"

BASIC_PKGS ?= "bash coreutils less net-tools util-linux vim"
APP_PKGS ?= "portaudio-v19 libgpiod"
RT_TEST_PKGS ?= "rt-tests"
CUSTOM_PKGS ?= "convolution-reverb gpio-toggle rt-test-scripts"

CONMANPKGS ?= "connman connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"

add_rootfs_version () {
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) \\\n \\\l\n" > ${IMAGE_ROOTFS}/etc/issue
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) %%h\n" > ${IMAGE_ROOTFS}/etc/issue.net
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue.net
}

add_home_root_symlink () {
    ln -sf ${ROOT_HOME} ${IMAGE_ROOTFS}/home/root
}

# Add the rootfs version to the welcome banner
ROOTFS_POSTPROCESS_COMMAND += " add_rootfs_version; add_home_root_symlink;"

IMAGE_INSTALL:append = " \
    packagegroup-boot \
    packagegroup-basic \ 
    packagegroup-tdx-cli \
    udev-extraconf \
    udev-extra-rules \
    ${BASIC_PKGS} \
    ${APP_PKGS} \
    ${CONMANPKGS} \
    ${CUSTOM_PKGS} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'timestamp-service systemd-analyze', '', d)} \
    media-files \
"
