package com.view;

import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpTest {
	private String community="gaokai";
	private Snmp snmp = null;
    private int version = 0;
    
    public void InitFactory(int version) {
        try {
            this.version = version;
            TransportMapping transprot = new DefaultUdpTransportMapping();
            snmp = new Snmp(transprot);
            if (this.version == SnmpConstants.version3) {
                //���ð�ȫģʽ
                USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
                SecurityModels.getInstance().addSecurityModel(usm);
            }
            transprot.listen();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Target createTarget(String IPAddress) {
        Target target = null;
        try {
            if (version != SnmpConstants.version3 && version != SnmpConstants.version1 && version != SnmpConstants.version2c) {
                System.out.println("");
                return target;
            }
            if (version == SnmpConstants.version3) {
                target = new UserTarget();
                //snmpV3��Ҫ���ð�ȫ����Ͱ�ȫ���ƣ����а�ȫ�����Ǵ���snmpָ��user���õ�new OctetString("SNMPV3")
                target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
                target.setSecurityName(new OctetString("SNMPV3"));
            } else {
                //snmpV1��snmpV2��Ҫָ������������
                target = new CommunityTarget();
                ((CommunityTarget) target).setCommunity(new OctetString(community));
                if (version == SnmpConstants.version2c) {
                    target.setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv2c);
                }
            }
            target.setVersion(version);
            //Ŀ������������
            target.setAddress(GenericAddress.parse(IPAddress));
            target.setRetries(5);
            target.setTimeout(1000);
        } catch (Exception ex) {
        } finally {
            return target;
        }
    }

    /**
     * ����PDU
     *
     * @param type
     * @param oid
     * @return
     */
    private PDU createPDU(int type, String oid) {
        PDU pdu = null;
        try {
            if (version == SnmpConstants.version3) {
                pdu = new ScopedPDU();
            } else {
                pdu = new PDUv1();
            }
            pdu.setType(type);
            pdu.add(new VariableBinding(new OID(oid)));
        } catch (Exception ex) {
        } finally {
            return pdu;
        }
    }
    
    private String IPAddressToSnmpFormat(String svrIP) {
        Integer port = 161;
        return "udp:" + svrIP + "/" + port.toString();
    }
	
    public void SnmpWalk(String svrIP, String oid) {
        try {
            //����Ŀ�����
            Target target = createTarget(IPAddressToSnmpFormat(svrIP));
            //��������
            PDU pdu = createPDU(PDU.GETNEXT, oid);
            //���ͱ��Ĳ���ȡ���ؽ��
            boolean matched = true;
            while (matched) {
                ResponseEvent responseEvent = snmp.send(pdu, target);
                if (responseEvent == null || responseEvent.getResponse() == null) {
                    break;
                }
                PDU response = responseEvent.getResponse();
                String nextOid = null;
                Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
                for (int i = 0; i < variableBindings.size(); i++)
                {
                    VariableBinding variableBinding=variableBindings.elementAt(i);
                    Variable variable=variableBinding.getVariable();
                    nextOid=variableBinding.getOid().toDottedString();
                    //�����������ڵ��µ�oid������ֹ���������������ֱܶ࣬��������������
                    if(!nextOid.startsWith(oid))
                    {
                        matched=false;
                        break;
                    }
                    String aaaa=variable.toString();
                    System.out.println(aaaa);
                }
                if(!matched)
                {
                    break;
                }
                pdu.clear();
                pdu.add(new VariableBinding(new OID(nextOid)));
            }
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        } finally {

        }
    }
}
