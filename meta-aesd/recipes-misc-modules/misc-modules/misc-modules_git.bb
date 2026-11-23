# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/ldd3.git;protocol=https;branch=kernel-6.18-updates \
           file://misc-modules-init \
           file://0001-Use-modprobe-instead-of-insmod.patch \
           "

# Modify these as desired
PV = "1.0+git"
SRCREV = "18b4c53b5a30263843434ea59399730084947b1a"

inherit module update-rc.d

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

INITSCRIPT_NAME = "misc-modules-init"
INITSCRIPT_PARAMS = "defaults 98"

# TMPDIR
INSANE_SKIP:${PN}-dbg += "buildpaths"
INSANE_SKIP:kernel-module-scull-${KERNEL_VERSION} += "buildpaths"
ERROR_QA:remove = "buildpaths"

do_compile() {
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules modules
}

do_install:append() {
# original from aesdsocket:
#	install -d ${D}${bindir}
#	install -m 0755 ${B}/aesdsocket ${D}${bindir}/

#	install -d ${D}${sysconfdir}/init.d
#	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d

# NEW
    install -d ${D}${bindir}
    install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}/
    install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}/

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/misc-modules-init ${D}${sysconfdir}/init.d/misc-modules-init

}

FILES:${PN} += " \
    ${bindir}/module_load \
    ${bindir}/module_unload \
    ${sysconfdir}/init.d/misc-modules-init \
"

