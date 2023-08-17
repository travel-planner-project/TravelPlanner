import { useState } from 'react'
import styles from './DropDown.module.scss'
import Icon from '../../Common/Icon'

type DropDownProps = {
  options: { title: string; key: number }[]
  onOptionChange: (selectedOption: string) => void
}

function DropDown({ options, onOptionChange }: DropDownProps) {
  const initialTitleValue = '일정 분류'
  const [isOptionOpened, setIsOptionOpened] = useState<boolean>(false)
  const [selectedOptionTitle, setSelectedOptionTitle] = useState<string>(initialTitleValue)

  const handleChangeCurrentOption = (option: string) => {
    setSelectedOptionTitle(option)
    setIsOptionOpened(false)

    // 부모 컴포넌트로 전달
    onOptionChange(option)
  }

  const handleOpenOptions = () => {
    setIsOptionOpened(prev => !prev)
  }

  return (
    <ul className={styles.listContainer}>
      <button
        className={isOptionOpened ? styles.open : styles.listTitle}
        type='button'
        onClick={handleOpenOptions}
      >
        <p
          className={
            selectedOptionTitle === initialTitleValue ? styles.initialTitle : styles.selectedTitle
          }
        >
          {selectedOptionTitle}
        </p>

        <Icon name='arrow-down' size={16} />
      </button>
      {isOptionOpened && (
        <div className={styles.listBox}>
          {options.map(option => {
            return (
              <li className={styles.list} key={option.key}>
                <button type='button' onClick={() => handleChangeCurrentOption(option.title)}>
                  {option.title}
                </button>
              </li>
            )
          })}
        </div>
      )}
    </ul>
  )
}

export default DropDown
