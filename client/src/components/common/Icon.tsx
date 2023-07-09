import SpriteIcon from '../../assets/sprite-icon.svg'

type IconNameType =
  | 'chatting-plus'
  | 'chatting-dots'
  | 'blue-clock'
  | 'bag-minus'
  | 'bag-plus'
  | 'box-arrow-left'
  | 'box-arrow-right'
  | 'calculator'
  | 'calendar'
  | 'camera'
  | 'check-box'
  | 'document'
  | 'x'
  | 'skyblue-x-circle'
  | 'person-plus'
  | 'pencil-box'
  | 'person'
  | 'trash-bin'
  | 'alarm-gray'
  | 'alarm-blue'
  | 'calculator-gray'
  | 'calculator-blue'
  | 'file-text-fill-gray'
  | 'file-text-fill-blue'
  | 'check-square-gray'
  | 'check-square-blue'

type IconProps = {
  name: IconNameType
  size?: 16 | 18 | 20 | 24 | 28 | 50 | 70
}

function Icon({ name, size = 24 }: IconProps) {
  return (
    <svg width={size} height={size}>
      <use href={`${SpriteIcon}#${name}`} />
    </svg>
  )
}

export default Icon
